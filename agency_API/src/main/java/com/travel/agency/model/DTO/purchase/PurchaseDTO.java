package com.travel.agency.model.DTO.purchase;


import com.travel.agency.enums.Status;
import com.travel.agency.model.DTO.TravelBundleDTO;
import com.travel.agency.model.entities.Purchase;

import java.time.LocalDateTime;
import java.util.List;

public record PurchaseDTO(
        Long id,
        List<TravelBundleDTO> travelBundles,
        Double totalPrice,
        LocalDateTime purchaseDate,
        String paymentMethod,
        Status status

) {
    // Constructor para convertir de Purchase a PurchaseDTO
    public PurchaseDTO(Purchase purchase) {
        this(
                purchase.getId(),
                purchase.getTravelBundles().stream()
                        .map(TravelBundleDTO::new)
                        .toList(),
                purchase.getTotalPrice(),
                purchase.getPurchaseDate(),
                purchase.getPaymentMethod() != null ? purchase.getPaymentMethod().toString() : null,
                purchase.getStatus()

        );
    }
}

