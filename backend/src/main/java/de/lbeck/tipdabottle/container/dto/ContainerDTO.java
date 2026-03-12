package de.lbeck.tipdabottle.container.dto;


import de.lbeck.tipdabottle.common.enums.ProductCategory;
import de.lbeck.tipdabottle.product.dto.ProductDTO;

public record ContainerDTO(
        long id,
        ProductDTO product,
        String name,
        String description,
        ProductCategory category,
        int capacity,
        int stock
) {
}
