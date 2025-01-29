package com.travel.agency.model.DTO.rating;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateRating(
        @NotNull
        Long id,
        @DecimalMin(value = "1.0", message = "Rating must be between 1 and 5.")
        @DecimalMax(value = "5.0", message = "Rating must be between 1 and 5.")
        Double rating,

        Long travelBundleId,

        @Size(max = 500, message = "Comment cannot be longer than 500 characters.")
        String comment

) {
}
