package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.entities.*;
import com.travel.agency.repository.*;
import com.travel.agency.utils.MapperUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final TravelBundleRepository travelBundleRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final DetailsPurchaseRepository detailsPurchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, TravelBundleRepository travelBundleRepository, UserRepository userRepository, ShoppingCartRepository shoppingCartRepository, DetailsPurchaseRepository detailsPurchaseRepository) {
        this.purchaseRepository = purchaseRepository;
        this.travelBundleRepository = travelBundleRepository;
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.detailsPurchaseRepository = detailsPurchaseRepository;
    }

    //CREAR COMPRA O "FACTURA DE COMPRA"
    @Transactional
    public PurchaseDTO createPurchase(String username) {
        //Buscar el carrito del usuario por username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        //Buscar carrito del usuario
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        //ver orElseThrow
        //Crear nueva compra
        Purchase purchase = new Purchase();
        purchase.setUser(shoppingCart.getUser());
        purchase.setTotalPrice(shoppingCart.getTotalPrice());
        purchase.setPurchaseDate(LocalDateTime.now());
        //Guardar compra para generar el ID
        Purchase savedPurchase = purchaseRepository.save(purchase);

        //---- ESTO LO REMPLAZO POR LO QUE SE HAGA EN DETAILSPURCHASE
        List<DetailsShoppingCart> shoppingCartDetails = shoppingCart.getDetailsShoppingCarts();
        List<DetailsPurchase> detailsPurchases = shoppingCartDetails.stream().map(cartDetail -> {
            DetailsPurchase detailsPurchase = new DetailsPurchase();
            detailsPurchase.setQuantity(cartDetail.getQuantity());
            detailsPurchase.setTotalPrice(cartDetail.getTotalPrice());
            detailsPurchase.setTravelBundle(cartDetail.getTravelBundle());
            detailsPurchase.setPurchase(savedPurchase); //SETEO A  LA COMPRA
            return detailsPurchase;
        }).collect(Collectors.toList());

        //hasta aca no iria. VER DETAILS PURCHASE
        //VACIAR CARRITO se crearia en el service de detailsShoppingCart o en ShoppingCart??
        //detailsShoppingCartService.clearShoppingCart(shoppingCart);
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
        //Existe la compra con ese ID?
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", purchaseId));
        purchaseRepository.deleteById(purchaseId);
    }

}

