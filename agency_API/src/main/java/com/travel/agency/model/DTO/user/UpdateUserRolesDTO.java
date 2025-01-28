package com.travel.agency.model.DTO.user;

import com.travel.agency.enums.Role;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UpdateUserRolesDTO(
        @NotEmpty
        Set<Role> roles
) {
}
