package de.lbeck.tipdabottle.account.dto.in;

import de.lbeck.tipdabottle.account.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RequestAccountCreateDTO(
        @NotEmpty
        @Email
        String email,
        @NotEmpty
        String password,
        @NotEmpty
        String firstName,
        @NotEmpty
        String lastName,
        @NotNull
        Role role
) {
}
