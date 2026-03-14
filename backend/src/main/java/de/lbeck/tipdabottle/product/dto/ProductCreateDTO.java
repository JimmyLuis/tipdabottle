package de.lbeck.tipdabottle.product.dto;

import de.lbeck.tipdabottle.common.enums.ProductCategory;
import de.lbeck.tipdabottle.container.dto.ContainerCreateDTO;

public record ProductCreateDTO(
        String name,
        String description,
        double price,
        ProductCategory category,
        int stock,
        ContainerCreateDTO container
) {

}
