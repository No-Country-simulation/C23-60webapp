package com.travel.agency.model.DTO.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRegisterDTO(
        @NotEmpty
        @Email
        String email,
        @NotEmpty
        String password
) {
}
