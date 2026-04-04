package de.lbeck.tipdabottle.purchase.dto.out;

import java.util.List;

public record ResponsePurchaseGroupPrivateDTO(
        Long id,
        List<ResponsePurchasePrivateDTO> items
) {
}
