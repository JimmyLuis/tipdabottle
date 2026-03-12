package de.lbeck.tipdabottle.purchase.dto;

import de.lbeck.tipdabottle.customer.dto.CustomerDTO;
import de.lbeck.tipdabottle.product.dto.ProductDTO;

import java.sql.Date;

public record PurchaseDTO(
        long id,
        long purchaseGroupId,
        ProductDTO product,
        CustomerDTO customer,
        Date creationDate,
        double worth,
        int quantity
) {
}
