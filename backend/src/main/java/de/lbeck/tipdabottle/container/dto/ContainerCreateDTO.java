package de.lbeck.tipdabottle.container.dto;

import de.lbeck.tipdabottle.common.enums.ProductCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ContainerCreateDTO(
        @NotEmpty
        String name,
        String description,
        @NotNull
        ProductCategory category,
        @Min(1)
        int capacity,
        @Min(0)
        int stock
) {
}
