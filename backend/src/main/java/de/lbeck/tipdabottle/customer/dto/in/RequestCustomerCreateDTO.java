package de.lbeck.tipdabottle.customer.dto.in;

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
