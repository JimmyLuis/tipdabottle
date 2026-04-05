package de.lbeck.tipdabottle.purchase.service;


import de.lbeck.tipdabottle.container.model.Container;
import de.lbeck.tipdabottle.customer.model.Customer;
import de.lbeck.tipdabottle.customer.service.CustomerService;
import de.lbeck.tipdabottle.product.model.Product;
import de.lbeck.tipdabottle.product.service.ProductService;
import de.lbeck.tipdabottle.purchase.dto.*;
import de.lbeck.tipdabottle.purchase.dto.in.RequestPurchaseCreateDTO;
import de.lbeck.tipdabottle.purchase.dto.in.RequestPurchaseReverseDTO;
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
    private final PurchaseGroupRepository purchaseGroupRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, PurchaseMapper purchaseMapper, ProductService productService, CustomerService customerService, PurchaseGroupRepository purchaseGroupRepository) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseMapper = purchaseMapper;
        this.productService = productService;
        this.customerService = customerService;
        this.purchaseGroupRepository = purchaseGroupRepository;
    }

    public Page<PurchaseGroup> getAllPurchases(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<PurchaseGroup> groupPage = purchaseGroupRepository.findGroupIdsOrdered(pageable);

        // optional: eager loading falls nötig
        List<Purchase> purchases = purchaseRepository.findAllByPurchaseGroupIn(groupPage.getContent());

        // wichtig: purchases in groups setzen (falls lazy)
        Map<Long, List<Purchase>> grouped = purchases.stream()
                .collect(Collectors.groupingBy(p -> p.getPurchaseGroup().getId()));

        groupPage.getContent().forEach(group ->
                group.setPurchaseList(grouped.getOrDefault(group.getId(), List.of()))
        );

        return groupPage;
    }

    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new PurchaseNotFoundException("Purchase with id " + id + " not found"));
    }

    public List<Purchase> getPurchasesByGroup(Long groupId) {
        List<Purchase> purchases = purchaseRepository.findAllByPurchaseGroup_Id(groupId);
        if (purchases.isEmpty()) {
            throw new PurchaseNotFoundException("Purchases within group id " + groupId + " not found");
        }
        return purchases;
    }

    public Page<Purchase> getPurchasesByCustomer(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("creationTime").descending()
        );
        Page<Purchase> purchasePage = purchaseRepository.findAllByCustomer_Id(customerId, pageable);
        //todo: getbyCustomer auch als purchaseGroup zurückgeben?!
        if (purchasePage.isEmpty()) {
            throw new PurchaseNotFoundException("Purchases of customer with id " + customerId + " not found");
        }
        return purchasePage;
    }

    public List<Purchase> createPurchases(Long customerId , List<RequestPurchaseCreateDTO> requestPurchaseCreateDTOList){
        if (requestPurchaseCreateDTOList.isEmpty()) throw new PurchaseDeniedException("Cant submit purchase without any products!");
        Customer customer = customerService.getCustomerById(customerId);
        validateCustomerPurchase(customer, requestPurchaseCreateDTOList);
        AtomicReference<Double> customerBalance = new AtomicReference<>(customer.getBalance());

        List<Product> productList = new ArrayList<>();
        PurchaseGroup group = purchaseGroupRepository.save(new PurchaseGroup());
        List<Purchase> purchases = new ArrayList<>();

        requestPurchaseCreateDTOList.forEach(purchase -> {
            Product curProduct = productService.getProductById(purchase.product_id());
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



    private Purchase submitPurchase(Customer customer, Product product, PurchaseGroup group, RequestPurchaseCreateDTO requestPurchaseCreateDTO) {

        Purchase purchase = new Purchase(
                null,
                group,
                product,
                customer,
                Timestamp.from(Instant.now()),
                product.getPrice() * requestPurchaseCreateDTO.quantity(),
                requestPurchaseCreateDTO.quantity(),
                false,
                null,
                null
        );

        return purchaseRepository.save(purchase);
    }

    private Product purchaseProduct(Product product, RequestPurchaseCreateDTO requestPurchaseCreateDTO){
        product.setStock(product.getStock() - requestPurchaseCreateDTO.quantity());
        Container prodContainer = product.getContainer();
        double containerStock = (double) product.getStock() / prodContainer.getCapacity();
        if (containerStock < prodContainer.getStock()){
            prodContainer.setStock((int) Math.ceil(containerStock));
        }
        return product;
    }

    private void validateCustomerPurchase(Customer customer, List<RequestPurchaseCreateDTO> requestPurchaseCreateDTOList){
        if (!customer.getActiveProfile()) throw new PurchaseDeniedException("Customer " + customer.getEmail() + " is not an active profile");
        if (customer.getLocked()) throw new PurchaseDeniedException("Customer " + customer.getEmail() + " is blocked for purchasing");

        requestPurchaseCreateDTOList.forEach(purchase -> {
            //validiert ob alle produkte kaufbar sind
            productService.getProductById(purchase.product_id());
        });
    }

    public List<Purchase> reversePurchases(Long customerId , List<RequestPurchaseReverseDTO> requestPurchaseReverseDTOList) {
        Customer customer = customerService.getCustomerById(customerId);
        validateCustomerPurchaseReverse(customer, requestPurchaseReverseDTOList);

        AtomicReference<Double> customerBalance = new AtomicReference<>(customer.getBalance());

        List<Product> productList = new ArrayList<>();

        List<Purchase> purchases = new ArrayList<>();

        PurchaseGroup reversedPurchaseGroup = purchaseGroupRepository.save(new PurchaseGroup());

        requestPurchaseReverseDTOList.forEach(purchase -> {
            Purchase purchaseEntity = purchaseRepository.findById(purchase.id()).get();
            Product curProduct = productService.getProductById(purchase.product_id());
            productList.add(reversePurchaseProduct(curProduct, purchaseEntity));
            customerBalance.accumulateAndGet(curProduct.getPrice() * purchaseEntity.getQuantity(), Double::sum);
            purchases.add(submitReversePurchase(customer, curProduct, reversedPurchaseGroup, purchaseEntity));
        });
        productList.forEach(productService::updateProductAndContainer);

        customer.setBalance(customerBalance.get());
        if (customer.getBalance() > -20.0) customer.setLocked(false); //todo properties value
        customerService.updateCustomer(customer);


        return purchases;
    }
    private Purchase submitReversePurchase(Customer customer, Product product, PurchaseGroup reversedGroup, Purchase purchase){

        Purchase reversedPurchase = new Purchase(
                null,
                reversedGroup,
                product,
                customer,
                Timestamp.from(Instant.now()),
                product.getPrice() * purchase.getQuantity(),
                purchase.getQuantity(),
                true,
                purchase.getId(),
                purchase.getPurchaseGroup().getId()
        );
        reversedPurchase = purchaseRepository.save(reversedPurchase);
        purchase.setReversedReference(reversedPurchase.getId());
        purchase.setReversedGroupReference(reversedPurchase.getPurchaseGroup().getId());
        purchaseRepository.save(purchase);
        return reversedPurchase;
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

    private void validateCustomerPurchaseReverse(Customer customer, List<RequestPurchaseReverseDTO> requestPurchaseReverseDTOList){
        if (!customer.getActiveProfile()) throw new PurchaseDeniedException("Customer " + customer.getEmail() + " is not an active profile");

        requestPurchaseReverseDTOList.forEach(purchase -> {
            //validiert ob alle purchases ex. und reversebar sind
            Purchase curPurchase = purchaseRepository.findById(purchase.id())
                            .orElseThrow(() -> new PurchaseNotFoundException("Purchase " + purchase.id() + " not found"));
            if (curPurchase.getCreationTime().before(Timestamp.from(Instant.now().minus(15, ChronoUnit.MINUTES)))) { //todo properties value
                throw new PurchaseDeniedException("Purchase with product name " + curPurchase.getProduct().getName() + " happened too long ago : " + curPurchase.getCreationTime());
            }
            if (curPurchase.getPurchaseGroup() == null) throw new PurchaseNotFoundException("Purchase group of purchase" + purchase.id() + " not found");
            if (curPurchase.getReversed() || curPurchase.getReversedReference() != null || curPurchase.getReversedGroupReference() != null) { //todo properties value
                throw new PurchaseDeniedException("Purchase with product name " + curPurchase.getProduct().getName() + " is already reversed");
            }
            if (!Objects.equals(curPurchase.getCustomer().getId(), customer.getId())){
                throw new PurchaseDeniedException("Customer " + customer.getEmail() + " is not the owner of the purchase");
            }
        });
    }
}
