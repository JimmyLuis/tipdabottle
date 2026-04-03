package de.lbeck.tipdabottle.purchase.controller;

import de.lbeck.tipdabottle.purchase.dto.in.RequestPurchaseCreateDTO;
import de.lbeck.tipdabottle.purchase.dto.in.RequestPurchaseReverseDTO;
import de.lbeck.tipdabottle.purchase.dto.out.ResponsePurchaseAsGroupDTO;
import de.lbeck.tipdabottle.purchase.dto.out.ResponsePurchaseDTO;
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

    @GetMapping
    public Page<ResponsePurchaseAsGroupDTO> getAllPurchases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") @Max(100) int size
    ){
        return purchaseService.getAllPurchases(page, size);
    }

    @GetMapping("/{id}")
    public ResponsePurchaseDTO getPurchaseById(@PathVariable Long id){
        return purchaseService.getPurchaseById(id);
    }

    @GetMapping("/group/{groupId}")
    public List<ResponsePurchaseDTO> getPurchasesByGroup(@PathVariable Long groupId){
        return purchaseService.getPurchasesByGroup(groupId);
    }

    @GetMapping("/customer/{customerId}")
    public Page<ResponsePurchaseDTO> getPurchasesByCustomer(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") @Max(100) int size){
        return purchaseService.getPurchasesByCustomer(customerId, page, size);
    }

    @PostMapping("/customer/{customerId}")
    public List<ResponsePurchaseDTO> createPurchases(@PathVariable Long customerId, @Valid @RequestBody List<@Valid RequestPurchaseCreateDTO> requestPurchaseCreateDTOList){
        return purchaseService.createPurchases(customerId, requestPurchaseCreateDTOList);
    }

    @DeleteMapping("/customer/{customerId}")
    public List<ResponsePurchaseDTO> deletePurchases(@PathVariable Long customerId, @Valid @RequestBody List<@Valid RequestPurchaseReverseDTO> requestPurchaseReverseDTOList){
        return purchaseService.reversePurchases(customerId, requestPurchaseReverseDTOList);
    }
}
