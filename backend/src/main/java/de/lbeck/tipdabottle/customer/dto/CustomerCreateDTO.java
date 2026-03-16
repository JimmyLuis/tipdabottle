package de.lbeck.tipdabottle.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CustomerCreateDTO(
        @NotEmpty
        String firstName,
        String lastName,
        @Email
        String email,
        Boolean community
) {
}
