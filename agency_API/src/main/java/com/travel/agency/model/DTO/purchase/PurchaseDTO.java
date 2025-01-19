package com.travel.agency.model.DTO.purchase;

import com.travel.agency.enums.Status;
import com.travel.agency.model.DTO.TravelBundleDTO;
import com.travel.agency.model.entities.Coupon;
import com.travel.agency.model.entities.User;

import java.time.LocalDateTime;
import java.util.List;

public record PurchaseDTO(
        Long id,
        //aca va userDTO
        User user,
        List<TravelBundleDTO> travelBundleDTOList,
        List<Coupon> coupon,
        Double totalPrice,
        LocalDateTime purchaseDate,
        String paymentMethod,
        Status status
) {
}
