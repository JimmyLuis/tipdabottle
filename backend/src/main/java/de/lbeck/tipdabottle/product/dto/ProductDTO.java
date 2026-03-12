package de.lbeck.tipdabottle.product.dto;

import de.lbeck.tipdabottle.common.enums.ProductCategory;
import de.lbeck.tipdabottle.container.dto.ContainerDTO;

public record ProductDTO(
        long id,
        String name,
        String description,
        double price,
        ProductCategory category,
        int stock,
        ContainerDTO container
) {
}
