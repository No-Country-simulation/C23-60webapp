package com.travel.agency.model.DTO.purchase;


import com.travel.agency.model.entities.DetailsPurchase;
import com.travel.agency.model.entities.Purchase;

import java.time.LocalDateTime;
import java.util.List;

public record PurchaseDTO(
        Long id,
        LocalDateTime purchaseDate,
        List<DetailsPurchase> detailsPurchase,
        //total price de la suma de todos los pquetes
        Double totalPrice

) {
    // Constructor para convertir de Purchase a PurchaseDTO
    public PurchaseDTO(Purchase purchase) {
        this(
                purchase.getId(),
                purchase.getPurchaseDate(),
                purchase.getDetailsPurchase(),
                purchase.getTotalPrice()
        );
    }
}

