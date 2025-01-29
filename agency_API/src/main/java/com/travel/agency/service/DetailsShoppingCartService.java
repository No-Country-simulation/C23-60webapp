package com.travel.agency.service;

import com.travel.agency.model.DTO.ShoppingCart.AddToShoppingCartDTO;
import com.travel.agency.model.entities.DetailsShoppingCart;
import com.travel.agency.model.entities.ShoppingCart;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.repository.DetailsShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
public class DetailsShoppingCartService {
    private final DetailsShoppingCartRepository detailsShoppingCartRepository;

    public DetailsShoppingCartService(DetailsShoppingCartRepository detailsShoppingCartRepository) {
        this.detailsShoppingCartRepository = detailsShoppingCartRepository;
    }
    
    public DetailsShoppingCart createShoppingCart(ShoppingCart shoppingCart, TravelBundle travelBundle,AddToShoppingCartDTO addToShoppingCartDTO) {
        DetailsShoppingCart details = new DetailsShoppingCart();
        details.setShoppingCart(shoppingCart);
        details.setTravelBundle(travelBundle);
        details.setQuantity(addToShoppingCartDTO.quantity());
        details.setTotalPrice(addToShoppingCartDTO.totalPrice());
        detailsShoppingCartRepository.save(details);
        return details;
    }
    
}
