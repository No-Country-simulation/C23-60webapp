package com.travel.agency.model.DTO.DetailsPurchase;

import com.travel.agency.model.entities.Purchase;
import com.travel.agency.model.entities.TravelBundle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailsPurchaseDTO {
    private Long id;
    private int quantity;
    private Double totalPrice;
    private TravelBundle travelBundle;
    private Purchase purchase;
    private Long couponId;
    
}
