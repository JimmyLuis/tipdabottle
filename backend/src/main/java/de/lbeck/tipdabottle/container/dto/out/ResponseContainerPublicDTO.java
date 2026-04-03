package de.lbeck.tipdabottle.container.dto.out;


import de.lbeck.tipdabottle.common.enums.ProductCategory;

public record ResponseContainerPublicDTO(
        Long id,
        String name,
        String description,
        ProductCategory category,
        int capacity,
        int stock
) {
}
