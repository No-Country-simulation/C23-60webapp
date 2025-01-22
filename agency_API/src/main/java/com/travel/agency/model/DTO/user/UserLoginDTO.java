package com.travel.agency.model.DTO.user;

import com.travel.agency.validation.AtLeastOneNotEmpty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@AtLeastOneNotEmpty
public record UserLoginDTO(
        @Email
        String email,
        String username,
        @NotEmpty
        String password
) {
}
