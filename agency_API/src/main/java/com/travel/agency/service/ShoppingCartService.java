package com.travel.agency.service;

import com.travel.agency.mapper.ShoppingCartMapper;
import com.travel.agency.model.DTO.ShoppingCart.AddToShoppingCartDTO;
import com.travel.agency.model.DTO.ShoppingCart.ShoppingCartDTO;
import com.travel.agency.model.DTO.ShoppingCart.UpdateDetailsShoppingCartDTO;
import com.travel.agency.model.entities.DetailsShoppingCart;
import com.travel.agency.model.entities.ShoppingCart;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
        shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCartDTO getShoppingCart() {
        User user = authService.getUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        return ShoppingCartMapper.toDTO(shoppingCart);
    }

    public ShoppingCart getShoppingCartEntity() {
        User user = authService.getUser();
        return user.getShoppingCart();
    }

    private void updateShoppingCartTotal(ShoppingCart shoppingCart) {
        double total = shoppingCart.getDetailsShoppingCarts().stream()
                .mapToDouble(DetailsShoppingCart::getTotalPrice)
                .sum();
        shoppingCart.setTotalPrice(total);
    }

    @Transactional
    public ShoppingCartDTO AddToShoppingCart(AddToShoppingCartDTO addToShoppingCartDTO) {
        ShoppingCart shoppingCart = this.getShoppingCartEntity();
        TravelBundle travelBundle = travelBundleService.findById(addToShoppingCartDTO.travelBundleId());

        if (travelBundle.getAvailableBundles() < addToShoppingCartDTO.quantity()) {
            throw new RuntimeException("Not enough available travel bundles");
        }
        detailsShoppingCartService.createDetailsShoppingCart(shoppingCart, travelBundle, addToShoppingCartDTO);
        updateShoppingCartTotal(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        return ShoppingCartMapper.toDTO(shoppingCart);
    }

    @Transactional
    public ShoppingCartDTO updateDetailShoppingCart(Long detailsId, UpdateDetailsShoppingCartDTO dto) {
        ShoppingCart shoppingCart = this.getShoppingCartEntity();
        detailsShoppingCartService.updateDetailsShoppingCart(detailsId, shoppingCart, dto);
        updateShoppingCartTotal(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        return ShoppingCartMapper.toDTO(shoppingCart);
    }

    @Transactional
    public void updateTravelUpdateShoppingCart(TravelBundle travelBundle) {
        List<DetailsShoppingCart> detailsList = detailsShoppingCartService.travelDetailsShoppingCart(travelBundle);
        detailsShoppingCartService.updateAllDetailsOfTravelBundle(detailsList, travelBundle);

        Set<ShoppingCart> cartsToUpdate = detailsList.stream()
                .map(DetailsShoppingCart::getShoppingCart)
                .collect(Collectors.toSet());

        for (ShoppingCart shoppingCart : cartsToUpdate) {
            updateShoppingCartTotal(shoppingCart);
        }
        shoppingCartRepository.saveAll(cartsToUpdate);
    }

    public ShoppingCartDTO deleteFromShoppingCart(Long detailsId) {
        ShoppingCart shoppingCart = this.getShoppingCartEntity();
        detailsShoppingCartService.deleteDetailsShoppingCart(detailsId, shoppingCart);
        updateShoppingCartTotal(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        return ShoppingCartMapper.toDTO(shoppingCart);
    }

    @Transactional
    public ShoppingCartDTO clearShoppingCart() {
        ShoppingCart shoppingCart = this.getShoppingCartEntity();
        shoppingCart.getDetailsShoppingCarts().clear();
        detailsShoppingCartService.deleteAllDetailsFromShoppingCart(shoppingCart);
        shoppingCart.setTotalPrice(0.0);
        shoppingCartRepository.save(shoppingCart);
        return ShoppingCartMapper.toDTO(shoppingCart);
    }

    @Transactional
    public void deleteTravelUpdateShoppingCart(TravelBundle travelBundle) {
        List<DetailsShoppingCart> detailsList = detailsShoppingCartService.travelDetailsShoppingCart(travelBundle);
        for (DetailsShoppingCart detail : detailsList) {
            ShoppingCart shoppingCart = detail.getShoppingCart();
            shoppingCart.getDetailsShoppingCarts().remove(detail);
            shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() - detail.getTotalPrice());
            shoppingCartRepository.save(shoppingCart);
        }
        detailsShoppingCartService.deleteAllDetailsOfTravelBundle(detailsList);
    }
}
