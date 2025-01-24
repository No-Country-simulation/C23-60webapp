package com.travel.agency.model.DTO.TravelBundle;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelBundleDTO {
    
    private Long id;
    private String title;
    private String description;
    private String destiny;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer availableBundles;
    private Double unitaryPrice;

    public TravelBundleDTO() {
    }    
        
}
