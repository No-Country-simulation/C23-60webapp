package com.travel.agency.mapper;

import com.travel.agency.model.DTO.rating.CreateRatingDTO;
import com.travel.agency.model.DTO.rating.RatingDTO;
import com.travel.agency.model.entities.Rating;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.model.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RatingMapper {
    public static RatingDTO toRatingDTO(Rating rating) {
        return new RatingDTO(
                rating.getId(),
                rating.getUser().getUsername(),
                rating.getRating(),
                rating.getTravelBundle().getId(),
                rating.getComment(),
                rating.getCreationDate()
        );
    }

    public static Rating toRating(CreateRatingDTO createRatingDTO, User user, TravelBundle travelBundle) {
        return new Rating(
                createRatingDTO.rating(),
                user,
                travelBundle,
                createRatingDTO.comment(),
                LocalDateTime.now()
        );
    }

    public static List<RatingDTO> toRatingDtoList(List<Rating> ratingList) {
        return ratingList.stream().map(RatingMapper::toRatingDTO)
                .collect(Collectors.toList());
    }
}
