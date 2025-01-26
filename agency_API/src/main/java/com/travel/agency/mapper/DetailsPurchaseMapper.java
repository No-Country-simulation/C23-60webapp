package com.travel.agency.mapper;

import com.travel.agency.model.DTO.DetailsPurchase.*;
import com.travel.agency.model.entities.DetailsPurchase;
import com.travel.agency.model.entities.TravelBundle;
import java.util.List;
import java.util.stream.Collectors;


public class DetailsPurchaseMapper {
    
    public static DetailsPurchase toEntity(DetailsPurchaseRequestDTO detailsPurchaseRequestDTO, TravelBundle travelBundle) {
        if (detailsPurchaseRequestDTO == null) {
            return null;
        }
        DetailsPurchase detailsPurchase = new DetailsPurchase();
        detailsPurchase.setQuantity(detailsPurchaseRequestDTO.getQuantity());
        detailsPurchase.setTotalPrice((detailsPurchaseRequestDTO.getTotalPrice()));
        detailsPurchase.setTravelBundle(travelBundle);
        return detailsPurchase;
    }

    public static DetailsPurchaseDTO toDTO(DetailsPurchase detailsPurchase) {
        if (detailsPurchase == null) {
            return null;
        }
        DetailsPurchaseDTO dto = new DetailsPurchaseDTO(
                detailsPurchase.getId(),
                detailsPurchase.getQuantity(),
                detailsPurchase.getTotalPrice(),
                null,
                null
        );
        return dto;
    }

    // Conversión de lista de entidades a lista de DTOs
    public static List<DetailsPurchaseDTO> toDTOList(List<DetailsPurchase> detailsPurchases) {
        return detailsPurchases.stream().map(DetailsPurchaseMapper::toDTO).collect(Collectors.toList());
    }
}
