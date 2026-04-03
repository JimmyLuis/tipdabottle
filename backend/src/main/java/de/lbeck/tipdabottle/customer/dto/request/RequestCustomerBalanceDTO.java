package de.lbeck.tipdabottle.customer.dto.request;

import jakarta.validation.constraints.NotNull;

public record RequestCustomerBalanceDTO(
        @NotNull
        Double addedBalance
) {
}
