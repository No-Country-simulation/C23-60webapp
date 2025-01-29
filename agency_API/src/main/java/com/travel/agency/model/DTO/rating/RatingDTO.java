package com.travel.agency.model.DTO.rating;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RatingDTO(
        Long id,
        String username,
        Double rating,
        Long travelBundleId,
        String comment,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime creationDate
) {
}
