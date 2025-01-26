package com.travel.agency.model.DTO.user;

import jakarta.validation.constraints.*;

public record UserRegisterDTO(
        //AGREGAR CAMPO DE DNI
        @NotEmpty
        String firstName,
        @NotEmpty
        String lastName,
        @NotNull
        @Pattern(regexp = "\\d{8,}", message = "Identity card number must be at least 8 digits length")
        String identityCard,
        @NotEmpty
        @Email
        String email,
        @NotEmpty
        String password
) {
}
