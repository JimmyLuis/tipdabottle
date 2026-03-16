package de.lbeck.tipdabottle.purchase.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public record PurchaseReverseDTO(
        @NotNull
        Long id,
        @NotNull
        Long product_id,
        @AssertTrue
        Boolean reversed
) {
}
