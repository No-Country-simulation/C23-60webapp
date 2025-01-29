package com.travel.agency.model.DTO.purchase;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.agency.mapper.DetailsPurchaseMapper;
import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseDTO;
import com.travel.agency.model.entities.Purchase;

import java.time.LocalDateTime;
import java.util.List;

public record PurchaseDTO(
        Long id,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime purchaseDate,
        List<DetailsPurchaseDTO> detailsPurchase,
        //total price de la suma de todos los pquetes
        Double totalPrice
) {
    // Constructor para convertir de Purchase a PurchaseDTO
    public PurchaseDTO(Purchase purchase) {
        this(
                purchase.getId(),
                purchase.getPurchaseDate(),
                DetailsPurchaseMapper.toDTOList(purchase.getDetailsPurchase()),
                purchase.getTotalPrice()
        );
    }
}

