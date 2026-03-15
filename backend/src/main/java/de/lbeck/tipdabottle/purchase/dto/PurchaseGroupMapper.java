package de.lbeck.tipdabottle.purchase.dto;

import de.lbeck.tipdabottle.purchase.model.PurchaseGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PurchaseMapper.class)
public interface PurchaseGroupMapper {
    PurchaseGroupDTO toDTO(PurchaseGroup purchaseGroup);
    PurchaseGroup toEntity(PurchaseGroupDTO purchaseGroupDTO);
}
