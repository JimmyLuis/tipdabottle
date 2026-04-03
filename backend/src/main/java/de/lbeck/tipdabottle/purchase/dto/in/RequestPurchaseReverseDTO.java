package de.lbeck.tipdabottle.purchase.dto.in;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public record RequestPurchaseReverseDTO(
        @NotNull
        Long id,
        @NotNull
        Long product_id,
        @AssertTrue
        Boolean reversed
) {
}
