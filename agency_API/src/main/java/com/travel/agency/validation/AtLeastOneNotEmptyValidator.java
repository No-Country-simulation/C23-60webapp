package com.travel.agency.validation;

import com.travel.agency.model.DTO.user.UserLoginDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class AtLeastOneNotEmptyValidator implements ConstraintValidator<AtLeastOneNotEmpty, UserLoginDTO> {

    @Override
    public boolean isValid(UserLoginDTO dto, ConstraintValidatorContext context) {
        return StringUtils.hasText(dto.username()) || StringUtils.hasText(dto.email());
    }
}