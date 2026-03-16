package de.lbeck.tipdabottle.customer.dto;

import jakarta.validation.constraints.NotNull;

public record CustomerBalanceDTO(
        @NotNull
        Double addedBalance
) {
}
