package com.travel.agency.mapper;

import com.travel.agency.model.DTO.ShoppingCart.DetailsShoppingCartDTO;
import com.travel.agency.model.entities.DetailsShoppingCart;

public class DetailsShoppingCartMapper {

    public static DetailsShoppingCartDTO toDTO(DetailsShoppingCart details) {
        return new DetailsShoppingCartDTO(
                details.getId(),
                details.getTravelBundle().getTitle(),
                details.getQuantity(),
                details.getTotalPrice()
        );
    }
}
