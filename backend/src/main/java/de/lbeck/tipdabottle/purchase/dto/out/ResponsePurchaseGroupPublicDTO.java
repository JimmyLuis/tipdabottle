package de.lbeck.tipdabottle.purchase.dto.out;

import java.util.List;

public record ResponsePurchaseGroupPublicDTO(
        Long id,
        List<ResponsePurchasePublicDTO> purchaseList
) {
}
