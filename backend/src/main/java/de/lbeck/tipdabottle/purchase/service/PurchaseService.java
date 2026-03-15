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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    public Page<PurchaseDTO> getAllPurchases(int page, int size){
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("creation_time").descending()
        );
        List<PurchaseDTO> purchaseDTOList = new ArrayList<>();
        Page<Purchase> purchasePage =  purchaseRepository.findAll(pageable);
        purchasePage.forEach(purchase -> purchaseDTOList.add(purchaseMapper.toDTO(purchase)));
        return new PageImpl<>(purchaseDTOList, pageable, purchasePage.getTotalElements());
    }

    public PurchaseDTO getPurchaseById(Long id){
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new PurchaseNotFoundException("Purchase with id " + id + " not found"));
        return purchaseMapper.toDTO(purchase);
    }

    public List<PurchaseDTO> getPurchasesByGroup(Long groupId){
        List<PurchaseDTO> purchaseDTOList = new ArrayList<>();
        purchaseRepository.findAllByPurchaseGroup_Id(groupId).forEach(purchase -> purchaseDTOList.add(purchaseMapper.toDTO(purchase)));
        if (purchaseDTOList.isEmpty()) throw new PurchaseNotFoundException("Purchases within group id " + groupId + " not found");
        return purchaseDTOList;
    }

    public Page<PurchaseDTO> getPurchasesByCustomer(Long customerId, int page, int size){
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("creation_time").descending()
        );
        List<PurchaseDTO> purchaseDTOList = new ArrayList<>();
        Page<Purchase> purchasePage =  purchaseRepository.findAllByCustomer_Id(customerId, pageable);
        if (purchasePage.isEmpty()) throw new PurchaseNotFoundException("Purchases of customer with id " + customerId + " not found");
        purchasePage.forEach(purchase -> purchaseDTOList.add(purchaseMapper.toDTO(purchase)));
        return new PageImpl<>(purchaseDTOList, pageable, purchasePage.getTotalElements());
    }

    public List<PurchaseCreateResponseDTO> createPurchases(Long customerId ,List<PurchaseCreateDTO> purchaseCreateDTOList){
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        validateCustomerPurchase(customerDTO, purchaseCreateDTOList);
        Customer customer = customerMapper.toEntity(customerDTO);
        AtomicReference<Double> customerBalance = new AtomicReference<>(customer.getBalance());

        List<Product> productList = new ArrayList<>();
        PurchaseGroup group = purchaseGroupRepository.save(new PurchaseGroup());
        List<PurchaseCreateResponseDTO> purchases = new ArrayList<>();

        purchaseCreateDTOList.forEach(purchase -> {
            Product curProduct = productMapper.toEntity(productService.getProductById(purchase.product_id()));
            productList.add(purchaseProduct(curProduct, purchase));
            customerBalance.accumulateAndGet(curProduct.getPrice(), (balance, cost) -> balance - cost);
            purchases.add(submitPurchase(customer, curProduct, group, purchase));
        });

        customer.setBalance(customerBalance.get());
        if (customer.getBalance() < -20.0) customer.setLocked(true); //todo properties value
        productList.forEach(productService::updateProductAndContainer);

        customerService.updateCustomer(customer);

        return purchases;
    }

    private PurchaseCreateResponseDTO submitPurchase(Customer customer, Product product, PurchaseGroup group, PurchaseCreateDTO purchaseCreateDTO){
        Purchase purchase = new Purchase(
                null,
                group,
                product,
                customer,
                Timestamp.from(Instant.now()),
                product.getPrice() * purchaseCreateDTO.quantity(),
                purchaseCreateDTO.quantity()
        );

        return purchaseResponseMapper.toDTO(purchaseRepository.save(purchase));
    }

    private Product purchaseProduct(Product product, PurchaseCreateDTO purchaseCreateDTO){
        product.setStock(product.getStock() - purchaseCreateDTO.quantity());
        Container prodContainer = product.getContainer();
        double containerStock = (double) product.getStock() / prodContainer.getCapacity();
        if (product.getStock() / prodContainer.getCapacity() < prodContainer.getStock()){
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


}
