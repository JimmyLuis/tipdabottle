package de.lbeck.tipdabottle.container.dto;

import de.lbeck.tipdabottle.common.enums.ProductCategory;

public record ContainerCreateDTO(
        String name,
        String description,
        ProductCategory category,
        int capacity,
        int stock
) {
}
