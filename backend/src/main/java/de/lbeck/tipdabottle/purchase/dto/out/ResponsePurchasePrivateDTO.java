package de.lbeck.tipdabottle.purchase.dto.out;

import de.lbeck.tipdabottle.product.dto.out.ResponseProductPublicDTO;

import java.sql.Timestamp;

public record ResponsePurchasePrivateDTO(
        Long id,
        Long purchaseGroupId,
        ResponseProductPublicDTO product,
        Long customerId,
        Timestamp creationTime,
        double worth,
        int quantity,
        Boolean reversed,
        Long reversedReference,
        Long reversedGroupReference
) {
}