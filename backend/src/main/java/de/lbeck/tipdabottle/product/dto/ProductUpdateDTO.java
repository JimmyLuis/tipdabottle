package de.lbeck.tipdabottle.product.dto;

import de.lbeck.tipdabottle.common.enums.ProductCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductUpdateDTO(
        @NotEmpty
        String name,
        String description,
        @Positive
        double price,
        @NotNull
        ProductCategory category,
        @Positive
        int stock) {
}
