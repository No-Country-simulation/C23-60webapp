package com.travel.agency.model.DTO.ShoppingCart;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;


public record UpdateDetailsShoppingCartDTO(
    @Positive Integer quantity,  
    @Positive @Digits(integer = 10, fraction = 2) Double totalPrice) {
}
