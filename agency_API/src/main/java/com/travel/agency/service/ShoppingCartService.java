package com.travel.agency.service;

import com.travel.agency.mapper.ShoppingCartMapper;
import com.travel.agency.model.DTO.ShoppingCart.AddToShoppingCartDTO;
import com.travel.agency.model.DTO.ShoppingCart.ShoppingCartDTO;
import com.travel.agency.model.entities.DetailsShoppingCart;
import com.travel.agency.model.entities.ShoppingCart;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final AuthService authService;
    private final DetailsShoppingCartService detailsShoppingCartService;
    private final TravelBundleService travelBundleService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, AuthService authService, DetailsShoppingCartService detailsShoppingCartService, TravelBundleService travelBundleService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.authService = authService;
        this.detailsShoppingCartService = detailsShoppingCartService;
        this.travelBundleService = travelBundleService;
    }

    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setTotalPrice(0.0);
        shoppingCart.setPurchaseDate(null);
        shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCartDTO getShoppingCart() {
        User user = authService.getUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        return ShoppingCartMapper.toDTO(shoppingCart);
    }

    public ShoppingCartDTO AddToShoppingCart(AddToShoppingCartDTO addToShoppingCartDTO) {
        User user = authService.getUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        TravelBundle travelBundle = travelBundleService.findById(addToShoppingCartDTO.travelBundleId());

        if (travelBundle.getAvailableBundles() < addToShoppingCartDTO.quantity()) {
            throw new RuntimeException("Not enough available travel bundles");
        }
        DetailsShoppingCart details = detailsShoppingCartService.createShoppingCart(shoppingCart, travelBundle, addToShoppingCartDTO);

        shoppingCart.setTotalPrice(details.getTotalPrice() + shoppingCart.getTotalPrice());
        shoppingCartRepository.save(shoppingCart);
        return ShoppingCartMapper.toDTO(shoppingCart);
    }

}
