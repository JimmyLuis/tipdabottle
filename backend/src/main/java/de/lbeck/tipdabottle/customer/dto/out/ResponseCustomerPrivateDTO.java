package de.lbeck.tipdabottle.customer.dto.out;

public record ResponseCustomerPrivateDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        double balance,
        Boolean locked,
        Boolean community
) {
}
