package com.travel.agency.model.DTO.purchase;

import jakarta.validation.constraints.NotNull;

public record CartRequest(
        @NotNull
        Long userId,
        @NotNull
        Long travelBundleId,
        Integer quantity
) {
}
