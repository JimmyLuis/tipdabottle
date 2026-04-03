package de.lbeck.tipdabottle.product.dto.out;

import de.lbeck.tipdabottle.common.enums.ProductCategory;
import de.lbeck.tipdabottle.container.dto.out.ResponseContainerPublicDTO;

public record ResponseProductPublicDTO(
        Long id,
        String name,
        String description,
        double price,
        ProductCategory category,
        int stock,
        ResponseContainerPublicDTO container
) {
}
