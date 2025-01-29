package com.travel.agency.model.DTO.DetailsPurchase;

import com.travel.agency.model.entities.Purchase;
import com.travel.agency.model.entities.TravelBundle;

public record DetailsPurchaseDTO(
        Long id,
        int quantity,
        Double totalPrice,
        TravelBundle travelBundle,
        Purchase purchase) {

}
