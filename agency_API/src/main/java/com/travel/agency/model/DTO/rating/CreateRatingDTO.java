package com.travel.agency.model.DTO.rating;

import jakarta.validation.constraints.*;

public record CreateRatingDTO(
        @NotNull(message = "Rating is required.")
        @DecimalMin(value = "1.0", message = "Rating must be between 1 and 5.")
        @DecimalMax(value = "5.0", message = "Rating must be between 1 and 5.")
        Double rating,

        @NotNull(message = "Travel bundle ID is required.")
        Long travelBundleId,

        @Size(max = 500, message = "Comment cannot be longer than 500 characters.")
        String comment
) {}
