package de.lbeck.tipdabottle.purchase.dto;

import de.lbeck.tipdabottle.customer.dto.response.ResponseCustomerDTO;
import de.lbeck.tipdabottle.product.dto.ProductDTO;

import java.sql.Timestamp;

public record PurchaseDTO(
        Long id,
        PurchaseGroupDTO purchaseGroup,
        ProductDTO product,
        ResponseCustomerDTO customer,
        Timestamp creationTime,
        double worth,
        int quantity,
        Boolean reversed,
        Long reversedReference,
        Long reversedGroupReference
) {
}
