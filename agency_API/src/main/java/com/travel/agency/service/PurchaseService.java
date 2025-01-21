package com.travel.agency.service;

import com.travel.agency.enums.Status;
import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.model.DTO.purchase.CartRequest;
import com.travel.agency.model.entities.Purchase;
import com.travel.agency.model.entities.TravelBundle;

import com.travel.agency.model.entities.User;
import com.travel.agency.repository.PurchaseRepository;
import com.travel.agency.repository.TravelBundleRepository;
import com.travel.agency.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    TravelBundleRepository travelBundleRepository;
    @Autowired
    UserRepository userRepository;

    //1 - CREAR / AGREGAR paquete a la compra
    @Transactional
    public Purchase addTravelBundleToCart(CartRequest cartRequest) {
        //Validar Usuario
        User user = userRepository.findById(cartRequest.userId())
                .orElseThrow(() -> new RuntimeException("User doesn´t exist"));

        //Buscar si el user tiene una compra acctiva PENDING
        Optional<Purchase> optionalPurchase = purchaseRepository.findByUserIdAndStatus(cartRequest.userId(), Status.PENDING);

        //Buscar el paquete seleccionado
        TravelBundle travelBundle = travelBundleRepository.findById(cartRequest.travelBundleId())
                .orElseThrow(() -> new RuntimeException("Travel Bundle not found"));

        //crear compra si no existe
        Purchase purchase = optionalPurchase.orElseGet(() -> {
            Purchase newPurchase = new Purchase();
            newPurchase.setUser(user); // Esto ya no debería generar error.
            newPurchase.setStatus(Status.PENDING);
            newPurchase.setTotalPrice(0.0);
            newPurchase.setPurchaseDate(LocalDateTime.now());
            return newPurchase;
        });

        //Agregar paquete a la compra
        purchase.addTravelBundle(travelBundle, cartRequest.quantity());

        //guardar compra en BBDD
        return purchaseRepository.save(purchase);
    }
    // 2 - Finalizar compra activa, cambiando estado a COMPLETED

    //3 - Ver todas las compras
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    // 4- Ver compra por ID
    public Purchase searchPurchaseById(Long idPurchase) {
        Optional<Purchase> purchase = purchaseRepository.findById(idPurchase);
        return purchase.orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", idPurchase));
    }

    //5 - ACTUALIZAR COMPRA (VER STATUS, TIENE QUE E3STAR PENDING)

    // 6 - ELIMINAR  compra entera  o VACIAR CARRITO
    public void deletePurchaseById(Long purchaseId) throws ResourceNotFoundException {
        //Existe la compra con ese ID?
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", purchaseId));
        //verificar antes de eliminar que todavia este pendiente de pago
        if (purchase.getStatus() != Status.PENDING) {
            throw new IllegalStateException("Cannot delete a Purchase");
        }
        //Buscar paquetes
        List<TravelBundle> travelBundles = purchase.getTravelBundles();
        //Restaurar disponibilidad de paquetes
        for (TravelBundle travelBundle : travelBundles){
            //aumentar en el inventario
            travelBundle.increaseAvailableBundles(travelBundle.getAmountToBuy());
            travelBundleRepository.save(travelBundle);
        }
        // Eliminar la relación de los paquetes con la compra
        purchase.setTravelBundles(new ArrayList<>());
        purchaseRepository.save(purchase);
        purchaseRepository.deleteById(purchaseId);

    }
    //7 - Pasar estado a CANCELADO (despues de estar pending un cierto tienpo?) Y/O si el usuario la cancela

    // 8 - eliminar yn paquete de la compra

}

