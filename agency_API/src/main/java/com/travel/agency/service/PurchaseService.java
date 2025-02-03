package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.mapper.DetailsPurchaseMapper;
import com.travel.agency.mapper.PurchaseMapper;
import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseDTO;
import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.entities.DetailsPurchase;
import com.travel.agency.model.entities.Purchase;
import com.travel.agency.model.entities.ShoppingCart;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final AuthService authService;
    private final ShoppingCartService shoppingCartService;

    public PurchaseService(PurchaseRepository purchaseRepository, AuthService authService, ShoppingCartService shoppingCartService) {
        this.purchaseRepository = purchaseRepository;
        this.authService = authService;
        this.shoppingCartService = shoppingCartService;
    }

    @Transactional
    public PurchaseDTO createPurchase(String username) {
        User user = authService.getUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        if (shoppingCart == null || shoppingCart.getDetailsShoppingCarts().isEmpty()) {
            throw new IllegalStateException("Shopping cart is empty.");
        }
        //Crear nueva compra
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setTotalPrice(shoppingCart.getTotalPrice());
        purchase.setPurchaseDate(LocalDateTime.now());
        // Crear detalles de compra a partir del carrito
        List<DetailsPurchase> detailsPurchases = DetailsPurchaseMapper.convertCartDetailsToPurchaseDetails(shoppingCart, purchase);
        //asociar y guradar compra con detalles
        purchase.setDetailsPurchase(detailsPurchases);
        Purchase savedPurchase = purchaseRepository.save(purchase);
        // Limpiar el carrito después de realizar la compra
        shoppingCartService.clearShoppingCart(); //VER ELIMINACION
        List<DetailsPurchaseDTO> detailsPurchaseDTOs = DetailsPurchaseMapper.toDTOList(detailsPurchases);
        return new PurchaseDTO(savedPurchase);
    }

    public List<PurchaseDTO> getAllPurchases() {
        List<Purchase> purchaseList = purchaseRepository.findAll();
        return PurchaseMapper.purchaseMapperDto(purchaseList);
    }

    public PurchaseDTO searchPurchaseById(Long idPurchase) {
        // Buscar la compra por ID
        Purchase purchase = purchaseRepository.findById(idPurchase)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", idPurchase));
        return new PurchaseDTO(purchase);
    }

    public List<PurchaseDTO> searchPurchaseByUser(String username) {
        User user = authService.getUser();
        List<Purchase> purchaseList = user.getPurchases();
        return PurchaseMapper.purchaseMapperDto(purchaseList);
    }

    //  ELIMINAR  compra entera , dejar? solo admin
    @Transactional
    public void deletePurchaseById(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", purchaseId));
        purchaseRepository.deleteById(purchaseId);
    }

}
