package de.lbeck.tipdabottle.customer.dto.out;

public record ResponseCustomerAdminDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        double balance,
        Boolean locked,
        Boolean community,
        Boolean activeProfile
) {
}
