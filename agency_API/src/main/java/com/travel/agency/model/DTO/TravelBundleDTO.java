package com.travel.agency.model.DTO;

import com.travel.agency.model.entities.TravelBundle;

public record TravelBundleDTO(
        Long id,
        String title,
        String destiny,
        Double unitaryPrice

) {
    
    public TravelBundleDTO (TravelBundle travelBundle){
        this(
                travelBundle.getId(),
                travelBundle.getTitle(),
                travelBundle.getDestiny(),
                travelBundle.getUnitaryPrice()
        );
    }
}
