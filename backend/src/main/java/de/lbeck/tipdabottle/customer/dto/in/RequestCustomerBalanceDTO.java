package de.lbeck.tipdabottle.customer.dto.in;

import jakarta.validation.constraints.NotNull;

public record RequestCustomerBalanceDTO(
        @NotNull
        Double addedBalance
) {
}
