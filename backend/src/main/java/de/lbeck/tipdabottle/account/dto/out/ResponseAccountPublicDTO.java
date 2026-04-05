package de.lbeck.tipdabottle.account.dto.out;

import de.lbeck.tipdabottle.account.enums.Role;
import de.lbeck.tipdabottle.customer.model.Customer;

public record ResponseAccountPublicDTO(
        String email,
        Role role,
        Customer customer,
        String token
) {
}
