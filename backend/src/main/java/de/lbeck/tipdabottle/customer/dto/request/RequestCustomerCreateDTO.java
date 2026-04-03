package de.lbeck.tipdabottle.customer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RequestCustomerCreateDTO(
        @NotEmpty
        String firstName,
        String lastName,
        @Email
        String email,
        Boolean community
) {
}
