package com.travel.agency.model.DTO.TravelBundle;

import com.travel.agency.model.DTO.image.ImageDTO;
import com.travel.agency.model.DTO.rating.RatingDTO;

import java.time.LocalDate;
import java.util.List;


public record TravelBundleDTO(
        Long id,
        String title,
        String description,
        String destiny,
        LocalDate startDate,
        LocalDate endDate,
        Integer availableBundles,
        Double unitaryPrice,
        List<RatingDTO> rating,
        List<ImageDTO> images

) {
        
}
