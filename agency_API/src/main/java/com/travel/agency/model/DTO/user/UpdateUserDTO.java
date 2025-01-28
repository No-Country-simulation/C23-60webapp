package com.travel.agency.model.DTO.user;

import java.time.LocalDate;

public record UpdateUserDTO(
        String firstName,
        String lastName,
        Integer identityCard,
        String email,
        String username,
        Integer phoneNumber
) {
}
