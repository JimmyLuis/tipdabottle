package de.lbeck.tipdabottle.purchase.controller;

import de.lbeck.tipdabottle.purchase.dto.*;
import de.lbeck.tipdabottle.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import org.springframework.data.domain.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public Page<PurchaseResponseAsGroupDTO> getAllPurchases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") @Max(100) int size
    ){
        return purchaseService.getAllPurchases(page, size);
    }

    @GetMapping("/{id}")
    public PurchaseDTO getPurchaseById(@PathVariable Long id){
        return purchaseService.getPurchaseById(id);
    }

    @GetMapping("/group/{groupId}")
    public List<PurchaseResponseDTO> getPurchasesByGroup(@PathVariable Long groupId){
        return purchaseService.getPurchasesByGroup(groupId);
    }

    @GetMapping("/customer/{customerId}")
    public Page<PurchaseResponseDTO> getPurchasesByCustomer(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") @Max(100) int size){
        return purchaseService.getPurchasesByCustomer(customerId, page, size);
    }

    @PostMapping("/customer/{customerId}")
    public List<PurchaseResponseDTO> createPurchases(@PathVariable Long customerId, @Valid @RequestBody List<@Valid PurchaseCreateDTO> purchaseCreateDTOList){
        return purchaseService.createPurchases(customerId, purchaseCreateDTOList);
    }

    @DeleteMapping("/customer/{customerId}")
    public List<PurchaseResponseDTO> deletePurchases(@PathVariable Long customerId, @Valid @RequestBody List<@Valid PurchaseReverseDTO> purchaseReverseDTOList){
        return purchaseService.reversePurchases(customerId, purchaseReverseDTOList);
    }
}
