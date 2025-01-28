package com.travel.agency.model.DTO.rating;

import com.travel.agency.model.DTO.user.MinUserDTO;

public record RatingDTO(
        Double rating,
        MinUserDTO user,
        String comment
) {
}
