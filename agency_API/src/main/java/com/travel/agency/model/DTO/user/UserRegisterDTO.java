package com.travel.agency.model.DTO.user;

import jakarta.validation.constraints.NotEmpty;

public record UserRegisterDTO(
        @NotEmpty
        String email,
        @NotEmpty
        String password
) {
}
