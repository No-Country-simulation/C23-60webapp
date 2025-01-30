package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.mapper.DetailsPurchaseMapper;
import com.travel.agency.mapper.DetailsShoppingCartMapper;
import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseDTO;
import com.travel.agency.model.entities.DetailsPurchase;
import com.travel.agency.model.entities.Purchase;
import com.travel.agency.model.entities.ShoppingCart;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.DetailsPurchaseRepository;
import com.travel.agency.repository.PurchaseRepository;
import com.travel.agency.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailsPurchaseService {


    private final DetailsPurchaseRepository detailsPurchaseRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final PurchaseRepository purchaseRepository;
    private final AuthService authService;

    public DetailsPurchaseService(DetailsPurchaseRepository detailsPurchaseRepository, ShoppingCartRepository shoppingCartRepository, PurchaseRepository purchaseRepository, AuthService authService) {
        this.detailsPurchaseRepository = detailsPurchaseRepository;
        this.authService = authService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Transactional
    public List<DetailsPurchaseDTO> createDetailsFromShoppingCart(Long purchaseId, String username) {
        User user = authService.getUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        if (shoppingCart == null || shoppingCart.getDetailsShoppingCarts().isEmpty()) {
            throw new IllegalStateException("Shopping cart is empty");
        }
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", purchaseId));
        //Convvertir y guardar cada detaaleCarrito a DetalleCompra
        List<DetailsPurchase> detailsPurchases = DetailsPurchaseMapper.convertCartDetailsToPurchaseDetails(shoppingCart, purchase);
        detailsPurchaseRepository.saveAll(detailsPurchases);
        // Limpiar carrito
        shoppingCart.getDetailsShoppingCarts().clear();
        shoppingCart.setTotalPrice(0.0);
        shoppingCartRepository.save(shoppingCart);
        //devolverDTos
        return DetailsPurchaseMapper.toDTOList(detailsPurchases);
    }


}
