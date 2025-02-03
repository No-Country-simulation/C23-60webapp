package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.exceptions.UnauthorizedAccessException;
import com.travel.agency.mapper.DetailsShoppingCartMapper;
import com.travel.agency.model.DTO.ShoppingCart.AddToShoppingCartDTO;
import com.travel.agency.model.DTO.ShoppingCart.UpdateDetailsShoppingCartDTO;
import com.travel.agency.model.entities.DetailsShoppingCart;
import com.travel.agency.model.entities.ShoppingCart;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.repository.DetailsShoppingCartRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DetailsShoppingCartService {

    private final DetailsShoppingCartRepository detailsShoppingCartRepository;

    public DetailsShoppingCartService(DetailsShoppingCartRepository detailsShoppingCartRepository) {
        this.detailsShoppingCartRepository = detailsShoppingCartRepository;
    }

    public DetailsShoppingCart findValideDetails(Long detailsId, ShoppingCart shoppingCart) {
        DetailsShoppingCart details = detailsShoppingCartRepository.findById(detailsId)
                .orElseThrow(() -> new ResourceNotFoundException("DetailsShoppingCart", "Id", detailsId));
        if (!details.getShoppingCart().equals(shoppingCart)) {
            throw new UnauthorizedAccessException("This detail does not belong to your shopping cart.");
        }
        return details;
    }

    public List<DetailsShoppingCart> travelDetailsShoppingCart(TravelBundle travelBundle) {
        return detailsShoppingCartRepository.findByTravelBundle(travelBundle);
    }

    public void createDetailsShoppingCart(ShoppingCart shoppingCart, TravelBundle travelBundle, AddToShoppingCartDTO addToShoppingCartDTO) {
        DetailsShoppingCart details = DetailsShoppingCartMapper.toEntity(shoppingCart, travelBundle, addToShoppingCartDTO);
        detailsShoppingCartRepository.save(details);
    }

    public void updateDetailsShoppingCart(Long detailsId, ShoppingCart shoppingCart, UpdateDetailsShoppingCartDTO dto) {
        DetailsShoppingCart details = this.findValideDetails(detailsId, shoppingCart);
        if (details.getTravelBundle().getAvailableBundles() < dto.quantity()) {
            throw new RuntimeException("Not enough available travel bundles");
        }
        DetailsShoppingCartMapper.updateEntity(details, dto);
        detailsShoppingCartRepository.save(details);
    }

    @Transactional
    public void updateAllDetailsOfTravelBundle(List<DetailsShoppingCart> detailsList, TravelBundle travelBundle) {
        for (DetailsShoppingCart detail : detailsList) {
            detail.setTotalPrice(detail.getQuantity() * travelBundle.getUnitaryPrice());
        }
        detailsShoppingCartRepository.saveAll(detailsList);
    }

    public void deleteDetailsShoppingCart(Long detailsId, ShoppingCart shoppingCart) {
        DetailsShoppingCart details = this.findValideDetails(detailsId, shoppingCart);
        detailsShoppingCartRepository.delete(details);
    }

    public void deleteAllDetailsFromShoppingCart(ShoppingCart shoppingCart) {
        List<DetailsShoppingCart> detailsList = detailsShoppingCartRepository.findByShoppingCart(shoppingCart);
        detailsShoppingCartRepository.deleteAll(detailsList);
    }

    public void deleteAllDetailsOfTravelBundle(List<DetailsShoppingCart> detailsList) {
        detailsShoppingCartRepository.deleteAll(detailsList);
    }
}
