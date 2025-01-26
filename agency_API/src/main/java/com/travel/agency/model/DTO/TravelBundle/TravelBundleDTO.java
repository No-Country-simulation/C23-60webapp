package com.travel.agency.model.DTO.TravelBundle;

import java.time.LocalDate;


public record TravelBundleDTO(
        Long id,
        String title,
        String description,
        String destiny,
        LocalDate startDate,
        LocalDate endDate,
        Integer availableBundles,
        Double unitaryPrice
) {
        
}
