package de.lbeck.tipdabottle.customer.dto;

public record CustomerDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        double balance,
        boolean locked,
        boolean community,
        boolean activeProfile
) {
}
