package de.lbeck.tipdabottle.purchase.dto.out;

import de.lbeck.tipdabottle.product.dto.out.ResponseProductPublicDTO;


public record ResponsePurchasePublicDTO(
        Long id,
        Long purchaseGroupId,
        ResponseProductPublicDTO product,
        double worth,
        int quantity,
        Boolean reversed,
        Long reversedReference,
        Long reversedGroupReference
) {
}
