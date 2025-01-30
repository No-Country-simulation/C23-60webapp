package com.travel.agency.model.DTO.DetailsPurchase;

import com.travel.agency.model.entities.DetailsPurchase;

public record DetailsPurchaseDTO(
        Long id,
        String travelBundleName,
        Integer quantity,
        Double totalPrice) {
    public DetailsPurchaseDTO(DetailsPurchase detailsPurchase) {
        this(
                detailsPurchase.getId(),
                detailsPurchase.getTravelBundle().getTitle(),
                detailsPurchase.getQuantity(),
                detailsPurchase.getTotalPrice()
        );

    }
}
