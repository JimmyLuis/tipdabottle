package de.lbeck.tipdabottle.customer.dto;

public record CustomerUpdateDTO(
        String firstName,
        String lastName,
        String email,
        boolean community,
        boolean activeProfile
) {
}
