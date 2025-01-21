package com.travel.agency.model.DTO.purchase;

public record CartRequest(
        Long userId,
        Long travelBundleId,
        Integer quantity
) {
}
