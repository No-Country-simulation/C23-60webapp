package com.travel.agency.model.DTO.rating;

import java.time.LocalDateTime;

public record RatingDTO(
        Long id,
        String username,
        Double rating,
        Long travelBundleId,
        String comment,
        LocalDateTime creationDate
) {
}
