package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.mapper.DetailsPurchaseMapper;
import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseDTO;
import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.entities.DetailsPurchase;
import com.travel.agency.model.entities.Purchase;
import com.travel.agency.model.entities.ShoppingCart;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.*;
import com.travel.agency.utils.MapperUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final TravelBundleRepository travelBundleRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final DetailsPurchaseRepository detailsPurchaseRepository;
    private final AuthService authService;
    private final DetailsPurchaseService detailsPurchaseService;

    public PurchaseService(PurchaseRepository purchaseRepository, TravelBundleRepository travelBundleRepository, UserRepository userRepository, ShoppingCartRepository shoppingCartRepository, DetailsPurchaseRepository detailsPurchaseRepository, AuthService authService, DetailsPurchaseService detailsPurchaseService) {
        this.purchaseRepository = purchaseRepository;
        this.travelBundleRepository = travelBundleRepository;
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.detailsPurchaseRepository = detailsPurchaseRepository;
        this.authService = authService;
        this.detailsPurchaseService = detailsPurchaseService;
    }

    //CREAR COMPRA O "FACTURA DE COMPRA"
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
        List<DetailsPurchase> detailsPurchases = detailsPurchaseService.createDetailsFromShoppingCart(purchase.getId(), purchase.getUser().getUsername());

        //asociar y guradar compra con detalles
        purchase.setDetailsPurchase(detailsPurchases);
        Purchase savedPurchase = purchaseRepository.save(purchase);

        // Limpiar el carrito después de realizar la compra
        //YA HAY METODO, TRAER PARA LIMPIAR
        shoppingCart.getDetailsShoppingCarts().clear();
        shoppingCartRepository.save(shoppingCart);

        List<DetailsPurchaseDTO> detailsPurchaseDTOs = DetailsPurchaseMapper.toDTOList(detailsPurchases);
        return new PurchaseDTO(savedPurchase);
    }

    //Ver todas las compras
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
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || user.get().getPurchases().isEmpty()) {
            throw new UsernameNotFoundException("No purchases available for the user: " + username);
        }
        List<Purchase> purchaseList = user.get().getPurchases();
        return MapperUtil.purchaseMapperDto(purchaseList);
    }

    //  ELIMINAR  compra entera  o VACIAR CARRITO
    @Transactional
    public void deletePurchaseById(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", purchaseId));
        purchaseRepository.deleteById(purchaseId);
    }

    //ver donde meter esto
    private List<DetailsPurchase> mapDetailsFromShoppingCart(ShoppingCart shoppingCart, Purchase purchase) {
        return shoppingCart.getDetailsShoppingCarts().stream()
                .map(detail -> {
                    DetailsPurchase detailsPurchase = new DetailsPurchase();
                    detailsPurchase.setTravelBundle(detail.getTravelBundle());
                    detailsPurchase.setQuantity(detail.getQuantity());
                    detailsPurchase.setTotalPrice(detail.getTotalPrice());
                    detailsPurchase.setPurchase(purchase);
                    return detailsPurchase;
                })
                .toList();
    }
}

