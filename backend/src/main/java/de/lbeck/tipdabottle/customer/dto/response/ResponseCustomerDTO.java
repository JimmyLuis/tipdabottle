package de.lbeck.tipdabottle.customer.dto.response;

public record ResponseCustomerDTO(
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
