package com.travel.agency.model.DTO.ShoppingCart;

import java.util.List;

public record ShoppingCartDTO(
        Double totalPrice,
        List<DetailsShoppingCartDTO> detailsShoppingCarts) {

}
