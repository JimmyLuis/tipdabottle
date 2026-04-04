package de.lbeck.tipdabottle.purchase.controller;

import de.lbeck.tipdabottle.common.annotations.View;
import de.lbeck.tipdabottle.purchase.dto.in.RequestPurchaseCreateDTO;
import de.lbeck.tipdabottle.purchase.dto.in.RequestPurchaseReverseDTO;
import de.lbeck.tipdabottle.purchase.dto.out.ResponsePurchaseAsGroupDTO;
import de.lbeck.tipdabottle.purchase.dto.out.ResponsePurchaseAdminDTO;
import de.lbeck.tipdabottle.purchase.model.Purchase;
import de.lbeck.tipdabottle.purchase.model.PurchaseGroup;
import de.lbeck.tipdabottle.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import org.springframework.data.domain.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @View(PurchaseGroup.class)
    @GetMapping
    public Page<PurchaseGroup> getAllPurchases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") @Max(100) int size
    ){
        return purchaseService.getAllPurchases(page, size);
    }

    @View(Purchase.class)
    @GetMapping("/{id}")
    public Purchase getPurchaseById(@PathVariable Long id){
        return purchaseService.getPurchaseById(id);
    }

    @View(Purchase.class)
    @GetMapping("/group/{groupId}")
    public List<Purchase> getPurchasesByGroup(@PathVariable Long groupId){
        return purchaseService.getPurchasesByGroup(groupId);
    }

    @View(Purchase.class)
    @GetMapping("/customer/{customerId}")
    public Page<Purchase> getPurchasesByCustomer(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") @Max(100) int size){
        return purchaseService.getPurchasesByCustomer(customerId, page, size);
    }

    @View(Purchase.class)
    @PostMapping("/customer/{customerId}")
    public List<Purchase> createPurchases(@PathVariable Long customerId, @Valid @RequestBody List<@Valid RequestPurchaseCreateDTO> requestPurchaseCreateDTOList){
        return purchaseService.createPurchases(customerId, requestPurchaseCreateDTOList);
    }

    @View(Purchase.class)
    @DeleteMapping("/customer/{customerId}")
    public List<Purchase> deletePurchases(@PathVariable Long customerId, @Valid @RequestBody List<@Valid RequestPurchaseReverseDTO> requestPurchaseReverseDTOList){
        return purchaseService.reversePurchases(customerId, requestPurchaseReverseDTOList);
    }
}
