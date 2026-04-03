package de.lbeck.tipdabottle.purchase.dto.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RequestPurchaseCreateDTO(
        @NotNull
        Long product_id,
        @Min(1)
        int quantity
) {
}
