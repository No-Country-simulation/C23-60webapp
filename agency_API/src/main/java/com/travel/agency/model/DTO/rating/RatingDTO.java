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
    public RatingDTO(Long id, String username, Double rating, Long travelBundleId, String comment, LocalDateTime creationDate) {
        this.id = id;
        this.username = username;
        this.rating = rating;
        this.travelBundleId = travelBundleId;
        this.comment = comment;
        this.creationDate = creationDate;
    }
}
