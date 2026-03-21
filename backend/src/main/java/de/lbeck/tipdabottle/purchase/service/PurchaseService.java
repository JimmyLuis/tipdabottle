package de.lbeck.tipdabottle.purchase.service;


import de.lbeck.tipdabottle.container.model.Container;
import de.lbeck.tipdabottle.customer.dto.CustomerDTO;
import de.lbeck.tipdabottle.customer.dto.CustomerMapper;
import de.lbeck.tipdabottle.customer.model.Customer;
import de.lbeck.tipdabottle.customer.service.CustomerService;
import de.lbeck.tipdabottle.product.dto.ProductMapper;
import de.lbeck.tipdabottle.product.model.Product;
import de.lbeck.tipdabottle.product.service.ProductService;
import de.lbeck.tipdabottle.purchase.dto.*;
import de.lbeck.tipdabottle.purchase.exception.PurchaseDeniedException;
import de.lbeck.tipdabottle.purchase.exception.PurchaseNotFoundException;
import de.lbeck.tipdabottle.purchase.model.Purchase;
import de.lbeck.tipdabottle.purchase.model.PurchaseGroup;
import de.lbeck.tipdabottle.purchase.repository.PurchaseGroupRepository;
import de.lbeck.tipdabottle.purchase.repository.PurchaseRepository;
import jakarta.persistence.OrderBy;
import org.hibernate.query.Order;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;
    private final ProductService productService;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final ProductMapper productMapper;
    private final PurchaseGroupRepository purchaseGroupRepository;
    private final PurchaseResponseMapper purchaseResponseMapper;

    public PurchaseService(PurchaseRepository purchaseRepository, PurchaseMapper purchaseMapper, ProductService productService, CustomerService customerService, CustomerMapper customerMapper, ProductMapper productMapper, PurchaseGroupRepository purchaseGroupRepository, PurchaseResponseMapper purchaseResponseMapper) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseMapper = purchaseMapper;
        this.productService = productService;
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.productMapper = productMapper;
        this.purchaseGroupRepository = purchaseGroupRepository;
        this.purchaseResponseMapper = purchaseResponseMapper;
    }

    public Page<PurchaseResponseAsGroupDTO> getAllPurchases(int page, int size){
        Pageable pageable = PageRequest.of(
                page,
                size
        );
        Page<PurchaseGroup> groupPage = purchaseGroupRepository.findGroupIdsOrdered(pageable);
        List<Purchase> entities = purchaseRepository.findAllByPurchaseGroupIn(groupPage.getContent());
        List<PurchaseResponseDTO> dtos = new ArrayList<>();
        entities.forEach(purchase -> dtos.add(purchaseResponseMapper.toDTO(purchase)));
        Map<Long, List<PurchaseResponseDTO>> mappedAndGroupedPurchases = dtos.stream().collect(Collectors.groupingBy(
                PurchaseResponseDTO::purchaseGroupId,
                () -> new TreeMap<>(Comparator.reverseOrder()),
                Collectors.toList()
        ));
        List<PurchaseResponseAsGroupDTO> responseDtos = new ArrayList<>();
        mappedAndGroupedPurchases.forEach((key, value) -> {
            responseDtos.add(new PurchaseResponseAsGroupDTO(key, value));
        });

        return new PageImpl<>(responseDtos, pageable, groupPage.getTotalElements());
    }

    public PurchaseDTO getPurchaseById(Long id){
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new PurchaseNotFoundException("Purchase with id " + id + " not found"));
        return purchaseMapper.toDTO(purchase);
    }

    public List<PurchaseResponseDTO> getPurchasesByGroup(Long groupId){
        List<PurchaseResponseDTO> purchaseDTOList = new ArrayList<>();
        purchaseRepository.findAllByPurchaseGroup_Id(groupId).forEach(purchase -> purchaseDTOList.add(purchaseResponseMapper.toDTO(purchase)));
        if (purchaseDTOList.isEmpty()) throw new PurchaseNotFoundException("Purchases within group id " + groupId + " not found");
        return purchaseDTOList;
    }

    public Page<PurchaseResponseDTO> getPurchasesByCustomer(Long customerId, int page, int size){
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("creationTime").descending()
        );
        List<PurchaseResponseDTO> purchaseDTOList = new ArrayList<>();
        Page<Purchase> purchasePage =  purchaseRepository.findAllByCustomer_Id(customerId, pageable);
        if (purchasePage.isEmpty()) throw new PurchaseNotFoundException("Purchases of customer with id " + customerId + " not found");
        purchasePage.forEach(purchase -> purchaseDTOList.add(purchaseResponseMapper.toDTO(purchase)));
        return new PageImpl<>(purchaseDTOList, pageable, purchasePage.getTotalElements());
    }

    public List<PurchaseResponseDTO> createPurchases(Long customerId , List<PurchaseCreateDTO> purchaseCreateDTOList){
        if (purchaseCreateDTOList.isEmpty()) throw new PurchaseDeniedException("Cant submit purchase without any products!");
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        validateCustomerPurchase(customerDTO, purchaseCreateDTOList);
        Customer customer = customerMapper.toEntity(customerDTO);
        AtomicReference<Double> customerBalance = new AtomicReference<>(customer.getBalance());

        List<Product> productList = new ArrayList<>();
        PurchaseGroup group = purchaseGroupRepository.save(new PurchaseGroup());
        List<PurchaseResponseDTO> purchases = new ArrayList<>();

        purchaseCreateDTOList.forEach(purchase -> {
            Product curProduct = productMapper.toEntity(productService.getProductById(purchase.product_id()));
            productList.add(purchaseProduct(curProduct, purchase));
            customerBalance.accumulateAndGet(curProduct.getPrice(), (balance, cost) -> balance - cost * purchase.quantity());
            purchases.add(submitPurchase(customer, curProduct, group, purchase));
        });

        customer.setBalance(customerBalance.get());
        if (!customer.getCommunity()){
            if (customer.getBalance() < -20.0) customer.setLocked(true); //todo properties value
        }
        productList.forEach(productService::updateProductAndContainer);

        customerService.updateCustomer(customer);

        return purchases;
    }



    private PurchaseResponseDTO submitPurchase(Customer customer, Product product, PurchaseGroup group, PurchaseCreateDTO purchaseCreateDTO){
        Purchase purchase = new Purchase(
                null,
                group,
                product,
                customer,
                Timestamp.from(Instant.now()),
                product.getPrice() * purchaseCreateDTO.quantity(),
                purchaseCreateDTO.quantity(),
                false,
                null
        );

        return purchaseResponseMapper.toDTO(purchaseRepository.save(purchase));
    }

    private Product purchaseProduct(Product product, PurchaseCreateDTO purchaseCreateDTO){
        product.setStock(product.getStock() - purchaseCreateDTO.quantity());
        Container prodContainer = product.getContainer();
        double containerStock = (double) product.getStock() / prodContainer.getCapacity();
        if (containerStock < prodContainer.getStock()){
            prodContainer.setStock((int) Math.ceil(containerStock));
        }
        return product;
    }

    private void validateCustomerPurchase(CustomerDTO customer, List<PurchaseCreateDTO> purchaseCreateDTOList){
        if (!customer.activeProfile()) throw new PurchaseDeniedException("Customer " + customer.email() + " is not an active profile");
        if (customer.locked()) throw new PurchaseDeniedException("Customer " + customer.email() + " is blocked for purchasing");

        purchaseCreateDTOList.forEach(purchase -> {
            //validiert ob alle produkte kaufbar sind
            productService.getProductById(purchase.product_id());
        });
    }

    public List<PurchaseResponseDTO> reversePurchases(Long customerId , List<PurchaseReverseDTO> purchaseReverseDTOList) {
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        validateCustomerPurchaseReverse(customerDTO, purchaseReverseDTOList);

        Customer customer = customerMapper.toEntity(customerDTO);
        AtomicReference<Double> customerBalance = new AtomicReference<>(customer.getBalance());

        List<Product> productList = new ArrayList<>();

        List<PurchaseResponseDTO> purchases = new ArrayList<>();

        purchaseReverseDTOList.forEach(purchase -> {
            Purchase purchaseEntity = purchaseRepository.findById(purchase.id()).get();
            Product curProduct = productMapper.toEntity(productService.getProductById(purchase.product_id()));
            productList.add(reversePurchaseProduct(curProduct, purchaseEntity));
            customerBalance.accumulateAndGet(curProduct.getPrice(), Double::sum);
            purchases.add(submitReversePurchase(customer, curProduct, purchaseEntity.getPurchaseGroup(), purchaseEntity));
        });
        productList.forEach(productService::updateProductAndContainer);

        customer.setBalance(customerBalance.get());
        if (customer.getBalance() > -20.0) customer.setLocked(false); //todo properties value
        customerService.updateCustomer(customer);


        return purchases;
    }
    private PurchaseResponseDTO submitReversePurchase(Customer customer, Product product, PurchaseGroup group, Purchase purchase){
        Purchase reversedPurchase = new Purchase(
                null,
                group,
                product,
                customer,
                Timestamp.from(Instant.now()),
                product.getPrice() * purchase.getQuantity(),
                purchase.getQuantity(),
                true,
                purchase.getId()
        );
        reversedPurchase = purchaseRepository.save(reversedPurchase);
        purchase.setReversedReverence(reversedPurchase.getId());
        purchaseRepository.save(purchase);
        return purchaseResponseMapper.toDTO(reversedPurchase);
    }

    private Product reversePurchaseProduct(Product product, Purchase purchase){
        product.setStock(product.getStock() + purchase.getQuantity());
        Container prodContainer = product.getContainer();
        double containerStock = (double) product.getStock() / prodContainer.getCapacity();
        if (containerStock < prodContainer.getStock()){
            prodContainer.setStock((int) Math.ceil(containerStock));
        }
        return product;
    }

    private void validateCustomerPurchaseReverse(CustomerDTO customer, List<PurchaseReverseDTO> purchaseReverseDTOList){
        if (!customer.activeProfile()) throw new PurchaseDeniedException("Customer " + customer.email() + " is not an active profile");

        purchaseReverseDTOList.forEach(purchase -> {
            //validiert ob alle purchases ex. und reversebar sind
            Purchase curPurchase = purchaseRepository.findById(purchase.id())
                            .orElseThrow(() -> new PurchaseNotFoundException("Purchase " + purchase.id() + " not found"));
            if (curPurchase.getCreationTime().before(Timestamp.from(Instant.now().minus(15, ChronoUnit.MINUTES)))) { //todo properties value
                throw new PurchaseDeniedException("Purchase with product name " + curPurchase.getProduct().getName() + " happened too long ago : " + curPurchase.getCreationTime());
            }
            if (curPurchase.getPurchaseGroup() == null) throw new PurchaseNotFoundException("Purchase group of purchase" + purchase.id() + " not found");
            if (curPurchase.getReversed() || curPurchase.getReversedReverence() != null) { //todo properties value
                throw new PurchaseDeniedException("Purchase with product name " + curPurchase.getProduct().getName() + " is already reversed");
            }
            if (!Objects.equals(curPurchase.getCustomer().getId(), customer.id())){
                throw new PurchaseDeniedException("Customer " + customer.email() + " is not the owner of the purchase");
            }
        });
    }
}
