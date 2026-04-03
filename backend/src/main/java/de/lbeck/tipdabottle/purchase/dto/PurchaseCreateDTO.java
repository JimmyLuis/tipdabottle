package de.lbeck.tipdabottle.purchase.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PurchaseCreateDTO(
        @NotNull
        Long product_id,
        @Min(1)
        int quantity
) {
}
