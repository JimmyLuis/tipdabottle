package de.lbeck.tipdabottle.product.dto;

import de.lbeck.tipdabottle.product.dto.in.RequestProductCreateDTO;
import de.lbeck.tipdabottle.product.dto.in.RequestProductUpdateDTO;
import de.lbeck.tipdabottle.product.dto.out.ResponseProductPublicDTO;
import de.lbeck.tipdabottle.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ResponseProductPublicDTO toPublicDTO(Product product);
    Product toEntity(ResponseProductPublicDTO responseProductPublicDTO);

    Product toEntity(RequestProductCreateDTO requestProductCreateDTO);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "container", ignore = true)
    Product toEntity(RequestProductUpdateDTO requestProductUpdateDTO, @MappingTarget Product product);
}
