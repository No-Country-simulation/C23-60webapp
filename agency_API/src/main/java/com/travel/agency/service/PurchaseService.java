package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.entities.*;
import com.travel.agency.repository.*;
import com.travel.agency.utils.PurchaseUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final PurchaseUtils purchaseUtils;
    private final ShoppingCartRepository shoppingCartRepository;
    private final DetailsPurchaseRepository detailsPurchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, TravelBundleRepository travelBundleRepository, UserRepository userRepository, PurchaseUtils purchaseUtils, ShoppingCartRepository shoppingCartRepository, DetailsPurchaseRepository detailsPurchaseRepository) {
        this.purchaseRepository = purchaseRepository;
        this.travelBundleRepository = travelBundleRepository;
        this.userRepository = userRepository;
        this.purchaseUtils = purchaseUtils;
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
        return purchaseUtils.purchaseMapperDto(purchaseList);
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
        return purchaseUtils.purchaseMapperDto(purchaseList);
    }

    //  ELIMINAR  compra entera  o VACIAR CARRITO
    @Transactional
    public void deletePurchaseById(Long purchaseId) throws ResourceNotFoundException {
        //Existe la compra con ese ID?
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", purchaseId));
        purchaseRepository.deleteById(purchaseId);
    }

    //Pasar estado a CANCELADO (despues de estar pending un cierto tiempo)
    //@Scheduled es una anotacion que permite ejecutar metodos de manera programada en intervalos
    //de tiempo definido
    // Método programado para revisar compras pendientes y cancelarlas si pasan 2 horas
//    @Scheduled(fixedRate = 7200000)
//    @Transactional
//    public void checkPendingPurchases(){
//  //Obtener todas las compras PENDING que fueron crreadas hace mas de 2 hs
//        LocalDateTime twoHoursAgo = LocalDateTime.now().minusHours(2);
//        //Buscar compras PENDING donde la creacion sea anterior al limite de compra
//        List<Purchase> pendingPurchases = purchaseRepository.findByStatusAndPurchaseDateBefore(Status.PENDING, twoHoursAgo);
//        //cancelar las compras que no se han confirmado a tiempo
//        for (Purchase purchase : pendingPurchases){
//            //Cencelo compra y devuelvo paquetes al stock
//            purchase.cancelPurchase();
//            //guardo la compra con estado CANCELADO
//            purchaseRepository.save(purchase);
//        }
//    }
//Corroborar compras canceladas
//    public List<PurchaseDTO> getCancelledPurchases(){
//        List<Purchase> purchaseList = purchaseRepository.findByStatus(Status.CANCELLED);
//        return  purchaseUtils.purchaseMapperDto(purchaseList);
//    }


}

