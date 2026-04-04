package de.lbeck.tipdabottle.purchase.dto;

import de.lbeck.tipdabottle.purchase.dto.out.ResponsePurchaseGroupAdminDTO;
import de.lbeck.tipdabottle.purchase.dto.out.ResponsePurchaseGroupPrivateDTO;
import de.lbeck.tipdabottle.purchase.dto.out.ResponsePurchaseGroupPublicDTO;
import de.lbeck.tipdabottle.purchase.model.PurchaseGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PurchaseMapper.class)
public interface PurchaseGroupMapper {
    PurchaseGroup toEntity(ResponsePurchaseGroupAdminDTO responsePurchaseGroupAdminDTO);

    ResponsePurchaseGroupPublicDTO toPublicDTO(PurchaseGroup purchaseGroup);
    ResponsePurchaseGroupPrivateDTO toPrivateDTO(PurchaseGroup purchaseGroup);
    ResponsePurchaseGroupAdminDTO toAdminDTO(PurchaseGroup purchaseGroup);
}
