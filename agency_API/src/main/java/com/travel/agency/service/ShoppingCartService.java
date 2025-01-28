package com.travel.agency.service;

import com.travel.agency.model.entities.ShoppingCart;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.ShoppingCartRepository;
import com.travel.agency.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
    }

    

    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setTotalPrice(0.0);
        shoppingCart.setPurchaseDate(null);
        shoppingCartRepository.save(shoppingCart);
    }
    /*
    public void getShoppingCart(Long id) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));
        //TravelBundle travelBundle = this.findById(id);
        //return TravelBundleMapper.toDTO(travelBundle);
    }
    */
}
