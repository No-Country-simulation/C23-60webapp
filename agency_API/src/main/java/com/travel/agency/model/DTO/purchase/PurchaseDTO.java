package com.travel.agency.model.DTO.purchase;


import com.travel.agency.model.entities.DetailsPurchase;
import com.travel.agency.model.entities.Purchase;

import java.time.LocalDateTime;
import java.util.List;

public record PurchaseDTO(
        Long id,
        Double totalPrice,
        LocalDateTime purchaseDate,
        List<DetailsPurchase> detailsPurchase

) {
    // Constructor para convertir de Purchase a PurchaseDTO
    public PurchaseDTO(Purchase purchase) {
        this(
                purchase.getId(),
                purchase.getTotalPrice(),
                purchase.getPurchaseDate(),
                purchase.getDetailsPurchase()
        );
    }
}

