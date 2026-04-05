package de.lbeck.tipdabottle.purchase.dto;

import de.lbeck.tipdabottle.purchase.dto.out.ResponsePurchaseGroupAdminDTO;
import de.lbeck.tipdabottle.purchase.dto.out.ResponsePurchaseGroupPrivateDTO;
import de.lbeck.tipdabottle.purchase.dto.out.ResponsePurchaseGroupPublicDTO;
import de.lbeck.tipdabottle.purchase.model.PurchaseGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PurchaseMapper.class)
public interface PurchaseGroupMapper {
    PurchaseGroup toEntity(ResponsePurchaseGroupAdminDTO responsePurchaseGroupAdminDTO);

    @Mapping(source = "purchaseList", target = "items")
    ResponsePurchaseGroupPublicDTO toPublicDTO(PurchaseGroup purchaseGroup);
    @Mapping(source = "purchaseList", target = "items")
    ResponsePurchaseGroupPrivateDTO toPrivateDTO(PurchaseGroup purchaseGroup);
    @Mapping(source = "purchaseList", target = "items")
    ResponsePurchaseGroupAdminDTO toAdminDTO(PurchaseGroup purchaseGroup);
}