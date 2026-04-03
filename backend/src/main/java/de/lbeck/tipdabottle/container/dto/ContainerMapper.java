package de.lbeck.tipdabottle.container.dto;

import de.lbeck.tipdabottle.container.dto.out.ResponseContainerPublicDTO;
import de.lbeck.tipdabottle.container.model.Container;
import de.lbeck.tipdabottle.product.dto.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ContainerMapper {

    ResponseContainerPublicDTO toPublicDTO(Container container);
    Container toEntity(ResponseContainerPublicDTO responseContainerPublicDTO);
}