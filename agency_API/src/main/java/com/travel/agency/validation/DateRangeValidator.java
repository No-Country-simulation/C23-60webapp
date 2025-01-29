package com.travel.agency.validation;

import com.travel.agency.model.DTO.TravelBundle.TravelBundleRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, TravelBundleRequestDTO> {
    @Override
    public boolean isValid(TravelBundleRequestDTO dto, ConstraintValidatorContext context) {
        if (dto.startDate() == null || dto.endDate() == null) {
            return true; // Null handled by @NotNull annotations
        }
        return dto.endDate().isAfter(dto.startDate());
    }
}
