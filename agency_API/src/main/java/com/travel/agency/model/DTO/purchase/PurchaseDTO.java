package com.travel.agency.model.DTO.purchase;


import com.travel.agency.mapper.DetailsPurchaseMapper;
import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseDTO;
import com.travel.agency.model.entities.DetailsPurchase;
import com.travel.agency.model.entities.Purchase;
import com.travel.agency.utils.MapperUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PurchaseDTO(
        Long id,
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

