package com.travel.agency.model.DTO.DetailsPurchase;

import com.travel.agency.model.entities.DetailsPurchase;

public record DetailsPurchaseDTO(
        Long id,
        String travelBundleName,
        Integer quantity,
        Double unitaryPrice,
        Double totalPrice) {
    public DetailsPurchaseDTO(DetailsPurchase detailsPurchase) {
        this(
                detailsPurchase.getId(),
                detailsPurchase.getTravelBundleTitle(),
                detailsPurchase.getQuantity(),
                detailsPurchase.getUnitPrice(),
                detailsPurchase.getTotalPrice()
        );

    }
}
