package de.lbeck.tipdabottle.purchase.dto;

import de.lbeck.tipdabottle.customer.dto.CustomerDTO;
import de.lbeck.tipdabottle.product.dto.ProductDTO;
import de.lbeck.tipdabottle.purchase.model.PurchaseGroup;

import java.sql.Date;
import java.sql.Timestamp;

public record PurchaseDTO(
        Long id,
        PurchaseGroupDTO purchaseGroup,
        ProductDTO product,
        CustomerDTO customer,
        Timestamp creationTime,
        double worth,
        int quantity,
        Boolean reversed,
        Long reversedReference,
        Long reversedGroupReference
) {
}
