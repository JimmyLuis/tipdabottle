package de.lbeck.tipdabottle.purchase.dto;

import java.util.List;

public record PurchaseResponseAsGroupDTO(
         Long id,
         List<PurchaseResponseDTO> items
) {

}
