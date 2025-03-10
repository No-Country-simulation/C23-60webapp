package com.travel.agency.model.DTO.user;

import com.travel.agency.enums.Role;

import java.time.LocalDate;
import java.util.Set;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        Integer identityCard,
        String email,
        String username,
        Integer phoneNumber,
        LocalDate registerDate,
        Set<Role> roles
) {
}
