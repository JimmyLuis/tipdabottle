package de.lbeck.tipdabottle.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CustomerUpdateDTO(
        @NotEmpty
        String firstName,
        String lastName,
        @Email
        String email,
        Boolean community,
        Boolean activeProfile
) {
}
