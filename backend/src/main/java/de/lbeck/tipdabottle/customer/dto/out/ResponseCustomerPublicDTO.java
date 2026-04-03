package de.lbeck.tipdabottle.customer.dto.out;

public record ResponseCustomerPublicDTO(
        Long id,
        String firstName,
        String lastName,
        Boolean locked,
        Boolean community
) {
}
