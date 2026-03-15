package de.lbeck.tipdabottle.product.dto;

import de.lbeck.tipdabottle.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO productDTO);

    Product toEntity(ProductCreateDTO productCreateDTO);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "container", ignore = true)
    Product toEntity(ProductUpdateDTO productUpdateDTO, @MappingTarget Product product);
}
