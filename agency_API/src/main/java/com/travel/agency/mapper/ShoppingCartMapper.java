package com.travel.agency.mapper;

import com.travel.agency.model.DTO.ShoppingCart.ShoppingCartDTO;
import com.travel.agency.model.entities.ShoppingCart;

public record ShoppingCartMapper() {

     public static ShoppingCartDTO toDTO(ShoppingCart shoppingCart) {
        return new ShoppingCartDTO(
                shoppingCart.getTotalPrice(),
                shoppingCart.getDetailsShoppingCarts().stream()
                        .map(DetailsShoppingCartMapper::toDTO)
                        .toList()
        );
    }
}
