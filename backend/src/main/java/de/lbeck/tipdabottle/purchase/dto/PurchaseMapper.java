package de.lbeck.tipdabottle.purchase.dto;

import de.lbeck.tipdabottle.customer.dto.CustomerMapper;
import de.lbeck.tipdabottle.product.dto.ProductMapper;
import de.lbeck.tipdabottle.purchase.dto.in.RequestPurchaseReverseDTO;
import de.lbeck.tipdabottle.purchase.dto.out.ResponsePurchaseAdminDTO;
import de.lbeck.tipdabottle.purchase.dto.out.ResponsePurchasePrivateDTO;
import de.lbeck.tipdabottle.purchase.dto.out.ResponsePurchasePublicDTO;
import de.lbeck.tipdabottle.purchase.model.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, ProductMapper.class, PurchaseGroupMapper.class})
public interface PurchaseMapper {

    Purchase toEntity(RequestPurchaseReverseDTO requestPurchaseReverseDTO);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "purchaseGroup.id", target = "purchaseGroupId")
    ResponsePurchaseAdminDTO toAdminDTO(Purchase purchase);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "purchaseGroup.id", target = "purchaseGroupId")
    ResponsePurchasePrivateDTO toPrivateDTO(Purchase purchase);

    @Mapping(source = "purchaseGroup.id", target = "purchaseGroupId")
    ResponsePurchasePublicDTO toPublicDTO(Purchase purchase);

}
