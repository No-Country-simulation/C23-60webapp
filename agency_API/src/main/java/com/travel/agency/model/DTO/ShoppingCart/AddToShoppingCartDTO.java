package com.travel.agency.model.DTO.ShoppingCart;

import jakarta.validation.constraints.*;


public record AddToShoppingCartDTO(
    @NotNull @Positive Long travelBundleId,  
    @NotNull @Positive Integer quantity,  
    @NotNull @Positive @Digits(integer = 10, fraction = 2) Double totalPrice) {
}
