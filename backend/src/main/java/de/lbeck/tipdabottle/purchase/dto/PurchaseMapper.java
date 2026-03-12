package de.lbeck.tipdabottle.purchase.dto;

import de.lbeck.tipdabottle.customer.dto.CustomerMapper;
import de.lbeck.tipdabottle.product.dto.ProductMapper;
import de.lbeck.tipdabottle.purchase.model.Purchase;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, ProductMapper.class})
public interface PurchaseMapper {
    PurchaseDTO toDTO(Purchase purchase);
    Purchase toEntity(PurchaseDTO purchaseDTO);
}
