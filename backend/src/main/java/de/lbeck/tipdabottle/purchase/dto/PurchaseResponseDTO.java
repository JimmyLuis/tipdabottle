package de.lbeck.tipdabottle.purchase.dto;

import de.lbeck.tipdabottle.product.dto.ProductDTO;

import java.sql.Timestamp;

public record PurchaseResponseDTO(
        Long id,
        Long purchaseGroupId,
        ProductDTO product,
        Long customerId,
        Timestamp creationTime,
        double worth,
        int quantity,
        Boolean reversed,
        Long reversedReference,
        Long reversedGroupReference
) {
}
