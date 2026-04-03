package de.lbeck.tipdabottle.purchase.dto.out;

import java.util.List;

public record ResponsePurchaseAsGroupDTO(
         Long id,
         List<ResponsePurchaseDTO> items
) {

}
