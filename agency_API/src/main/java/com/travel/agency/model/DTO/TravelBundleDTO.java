package com.travel.agency.model.DTO;

import com.travel.agency.model.entities.Coupon;

import java.time.LocalDate;
import java.util.List;

public record TravelBundleDTO(
        Long id,
        String title,
        String description,
        String destiny,
        LocalDate startDate,
        LocalDate endDate,
        Integer totalBundles,
        List<Coupon> coupon,
        Integer amountToBuy,
        Double unitaryPrice,
        Double totalPrice

        ) {
}
