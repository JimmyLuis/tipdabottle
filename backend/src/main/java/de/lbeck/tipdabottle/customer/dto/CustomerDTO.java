package de.lbeck.tipdabottle.customer.dto;

public record CustomerDTO(
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
