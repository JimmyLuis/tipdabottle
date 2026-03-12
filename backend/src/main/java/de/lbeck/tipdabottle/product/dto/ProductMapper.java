package de.lbeck.tipdabottle.product.dto;

import de.lbeck.tipdabottle.product.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO productDTO);
}
