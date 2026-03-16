package de.lbeck.tipdabottle.purchase.dto;

import de.lbeck.tipdabottle.customer.dto.CustomerMapper;
import de.lbeck.tipdabottle.product.dto.ProductMapper;
import de.lbeck.tipdabottle.purchase.model.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, ProductMapper.class, PurchaseGroupMapper.class})
public interface PurchaseResponseMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "purchaseGroup.id", target = "purchaseGroupId")
    PurchaseResponseDTO toDTO(Purchase purchase);

}
