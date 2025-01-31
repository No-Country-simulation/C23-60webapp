package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.mapper.DetailsPurchaseMapper;
import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.entities.DetailsPurchase;
import com.travel.agency.model.entities.Purchase;
import com.travel.agency.model.entities.ShoppingCart;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.DetailsPurchaseRepository;
import com.travel.agency.repository.PurchaseRepository;
import com.travel.agency.repository.ShoppingCartRepository;
import com.travel.agency.repository.UserRepository;
import com.travel.agency.utils.MapperUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    ;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final AuthService authService;
    private final ShoppingCartService shoppingCartService;
    private final DetailsPurchaseRepository detailsPurchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, UserRepository userRepository, ShoppingCartRepository shoppingCartRepository, AuthService authService, ShoppingCartService shoppingCartService, DetailsPurchaseRepository detailsPurchaseRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.authService = authService;
        this.shoppingCartService = shoppingCartService;
        this.detailsPurchaseRepository = detailsPurchaseRepository;
    }

    //CREAR COMPRA O "FACTURA DE COMPRA"
    @Transactional
    public PurchaseDTO createPurchase(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

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
        //Persistir la compra y detalles
        Purchase savedPurchase = purchaseRepository.save(purchase);
        detailsPurchaseRepository.saveAll(detailsPurchases);
        // Limpiar el carrito después de realizar la compra
        shoppingCartService.clearShoppingCart();
//        shoppingCart.getDetailsShoppingCarts().clear();
//        shoppingCart.setTotalPrice(0.0);
//        shoppingCartRepository.save(shoppingCart);
        return new PurchaseDTO(savedPurchase);
    }

    //Ver todas las compras PARA ADMIN
    public List<PurchaseDTO> getAllPurchases() {
        List<Purchase> purchaseList = purchaseRepository.findAll();
        return MapperUtil.purchaseMapperDto(purchaseList);
    }

    // Ver compra por ID
    public PurchaseDTO searchPurchaseById(Long idPurchase) {
        // Buscar la compra por ID
        Purchase purchase = purchaseRepository.findById(idPurchase)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", idPurchase));
        return new PurchaseDTO(purchase);
    }

    // Ver compras por user
    public List<PurchaseDTO> searchPurchaseByUser(String username) {
        User user = authService.getUser();
        List<Purchase> purchaseList = user.getPurchases();
        return MapperUtil.purchaseMapperDto(purchaseList);
    }

    //  ELIMINAR  compra entera  o VACIAR CARRITO
    @Transactional
    public void deletePurchaseById(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", purchaseId));
        purchaseRepository.deleteById(purchaseId);
    }

}

