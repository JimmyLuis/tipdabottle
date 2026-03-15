package de.lbeck.tipdabottle.purchase.dto;

import de.lbeck.tipdabottle.customer.dto.CustomerDTO;
import de.lbeck.tipdabottle.product.dto.ProductDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

public record PurchaseCreateDTO(
        @NotNull
        Long product_id,
        @Min(1)
        int quantity
) {
}
