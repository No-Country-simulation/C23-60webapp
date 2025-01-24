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
        detailsPurchase.setTotal_price(detailsPurchaseRequestDTO.getTotalPrice());
        detailsPurchase.setTravelBundle(travelBundle);
        return detailsPurchase;
    }

    public static DetailsPurchaseDTO toDTO(DetailsPurchase detailsPurchase) {
        if (detailsPurchase == null) {
            return null;
        }
        DetailsPurchaseDTO dto = new DetailsPurchaseDTO();
        dto.setId(detailsPurchase.getId());
        dto.setQuantity(detailsPurchase.getQuantity());
        dto.setTotalPrice(detailsPurchase.getTotal_price());      
        dto.setCouponId(detailsPurchase.getCoupon() != null ? detailsPurchase.getCoupon().getId() : null);
        return dto;
    }

    // Conversión de lista de entidades a lista de DTOs
    public static List<DetailsPurchaseDTO> toDTOList(List<DetailsPurchase> detailsPurchases) {
        return detailsPurchases.stream().map(DetailsPurchaseMapper::toDTO).collect(Collectors.toList());
    }
}
