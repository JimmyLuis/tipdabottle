package de.lbeck.tipdabottle.purchase.dto.out;

import java.util.List;

public record ResponsePurchaseGroupAdminDTO(
        Long id,
        List<ResponsePurchaseAdminDTO> purchaseList
) {
}
