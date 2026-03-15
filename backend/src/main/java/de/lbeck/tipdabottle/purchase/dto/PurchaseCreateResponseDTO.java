package de.lbeck.tipdabottle.purchase.dto;

import de.lbeck.tipdabottle.customer.dto.CustomerDTO;
import de.lbeck.tipdabottle.product.dto.ProductDTO;

import java.sql.Timestamp;

public record PurchaseCreateResponseDTO(
        Long id,
        Long purchaseGroupId,
        ProductDTO product,
        Long customerId,
        Timestamp creationTime,
        double worth,
        int quantity) {
}
