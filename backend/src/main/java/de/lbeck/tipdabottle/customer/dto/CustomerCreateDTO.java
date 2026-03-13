package de.lbeck.tipdabottle.customer.dto;

public record CustomerCreateDTO(
        String firstName,
        String lastName,
        String email,
        boolean community
) {
}
