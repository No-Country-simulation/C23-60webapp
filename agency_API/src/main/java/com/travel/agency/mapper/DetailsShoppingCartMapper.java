package com.travel.agency.mapper;

import com.travel.agency.model.DTO.ShoppingCart.AddToShoppingCartDTO;
import com.travel.agency.model.DTO.ShoppingCart.DetailsShoppingCartDTO;
import com.travel.agency.model.DTO.ShoppingCart.UpdateDetailsShoppingCartDTO;
import com.travel.agency.model.entities.DetailsShoppingCart;
import com.travel.agency.model.entities.ShoppingCart;
import com.travel.agency.model.entities.TravelBundle;

public class DetailsShoppingCartMapper {

    public static DetailsShoppingCartDTO toDTO(DetailsShoppingCart details) {
        return new DetailsShoppingCartDTO(
                details.getId(),
                details.getTravelBundle().getId(),
                details.getTravelBundle().getTitle(),
                details.getQuantity(),
                details.getTotalPrice()
        );
    }

    public static DetailsShoppingCart toEntity(ShoppingCart shoppingCart, TravelBundle travelBundle, AddToShoppingCartDTO dto) {
        DetailsShoppingCart details = new DetailsShoppingCart();
        details.setShoppingCart(shoppingCart);
        details.setTravelBundle(travelBundle);
        details.setQuantity(dto.quantity());
        details.setTotalPrice(dto.totalPrice());
        return details;
    }
    
    public static void updateEntity(DetailsShoppingCart details, UpdateDetailsShoppingCartDTO dto) {
    if (dto.quantity() != null) {
        details.setQuantity(dto.quantity());
    }
    if (dto.totalPrice() != null) {
        details.setTotalPrice(dto.totalPrice());
    }
}
    
}
