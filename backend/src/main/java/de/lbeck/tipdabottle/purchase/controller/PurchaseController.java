package de.lbeck.tipdabottle.purchase.controller;

import de.lbeck.tipdabottle.purchase.dto.PurchaseCreateDTO;
import de.lbeck.tipdabottle.purchase.dto.PurchaseCreateResponseDTO;
import de.lbeck.tipdabottle.purchase.dto.PurchaseDTO;
import de.lbeck.tipdabottle.purchase.exception.PurchaseNotFoundException;
import de.lbeck.tipdabottle.purchase.model.Purchase;
import de.lbeck.tipdabottle.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import org.springframework.data.domain.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public Page<PurchaseDTO> getAllPurchases(
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
    public List<PurchaseDTO> getPurchasesByGroup(@PathVariable Long groupId){
        return purchaseService.getPurchasesByGroup(groupId);
    }

    @GetMapping("/customer/{customerId}")
    public Page<PurchaseDTO> getPurchasesByCustomer(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") @Max(100) int size){
        return purchaseService.getPurchasesByCustomer(customerId, page, size);
    }

    @PostMapping("/customer/{customerId}")
    public List<PurchaseCreateResponseDTO> createPurchases(@PathVariable Long customerId, @Valid @RequestBody List<PurchaseCreateDTO> purchaseCreateDTOList){
        return purchaseService.createPurchases(customerId, purchaseCreateDTOList);
    }
}
