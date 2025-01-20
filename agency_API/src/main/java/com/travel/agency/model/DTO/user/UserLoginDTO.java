package com.travel.agency.model.DTO.user;

import com.travel.agency.validation.AtLeastOneNotEmpty;
import jakarta.validation.constraints.NotEmpty;

@AtLeastOneNotEmpty
public record UserLoginDTO(
        @NotEmpty
        String email,
        String username,
        @NotEmpty
        String password
) {
}
