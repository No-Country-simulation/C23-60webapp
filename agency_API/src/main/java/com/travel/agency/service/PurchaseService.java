package com.travel.agency.service;

import com.travel.agency.enums.Status;
import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.model.DTO.purchase.CartRequest;
import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.DTO.purchase.UpdatePurchase;
import com.travel.agency.model.entities.Purchase;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.PurchaseRepository;
import com.travel.agency.repository.TravelBundleRepository;
import com.travel.agency.repository.UserRepository;
import com.travel.agency.utils.PurchaseUtils;
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
    @Autowired
    PurchaseUtils purchaseUtils;

    //CREAR / AGREGAR paquete a la compra
    @Transactional
    public PurchaseDTO addTravelBundleToCart(CartRequest cartRequest) {
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
            newPurchase.setUser(user);
            newPurchase.setStatus(Status.PENDING);
            newPurchase.setTotalPrice(0.0);
            newPurchase.setPurchaseDate(LocalDateTime.now());
            return newPurchase;
        });

        //Agregar paquete a la compra
        purchase.addTravelBundle(travelBundle, cartRequest.quantity());

        //guardar compra en BBDD
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return new PurchaseDTO(savedPurchase);
    }

    // Finalizar compra activa, cambiando estado a COMPLETED
    @Transactional
    public PurchaseDTO finalizePurchase(Long idPurchase, String paymentMethod) {
        Purchase purchase = purchaseRepository.findById(idPurchase)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", idPurchase));

        if (purchase.getStatus() != Status.PENDING) {
            throw new IllegalStateException("Purchase isn´t in PENDING status and cannot be finalized");
        }
        //Setear la forma de pago y cambiar el estado a confirmed
        purchase.setPaymentMethod(paymentMethod);
        purchase.setStatus(Status.CONFIRMED);

        //Guardar en la base de datos
        Purchase updatedPurchase = purchaseRepository.save(purchase);
        //convierto a DTO
        return purchaseUtils.convertToPurchaseDto(updatedPurchase);
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

        // Retornar el PurchaseDTO directamente
        return new PurchaseDTO(purchase);
    }

    // Ver compras por user
    public List<PurchaseDTO> searchPurchaseByUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || user.get().getPurchases() == null) {
            throw new RuntimeException("User not found or no purchases available");
        }
        List<Purchase> purchaseList = user.get().getPurchases();
        return purchaseUtils.purchaseMapperDto(purchaseList);

    }

    //CTUALIZAR COMPRA (VER STATUS, TIENE QUE E3STAR PENDING)
    @Transactional
    public PurchaseDTO updatePurchase(Long purchaseId, UpdatePurchase updatePurchase) {
        //Existe la compra con ese ID?
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", purchaseId));
        //verificar antes de eliminar que todavia este pendiente de pago
        if (purchase.getStatus() != Status.PENDING) {
            throw new IllegalStateException("Only purchases in PENDING status can be modified");
        }

        //Buscar el paquete dentro de la compra
        TravelBundle travelBundle = purchase.getTravelBundles().stream()
                .filter(tb -> tb.getId().equals(updatePurchase.travelBundleId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Travel bundle", "ID", updatePurchase.travelBundleId()));
        //obtener cantidad actual
        int currentAmount = travelBundle.getAmountToBuy();
        //si la nueva cantidad es mayor, restar del stock disponible
        if (updatePurchase.newAmountToBuy() > currentAmount) {
            int difference = updatePurchase.newAmountToBuy() - currentAmount;
            //resto del stock
            travelBundle.decreaseAvaliableBundles(difference);
            //si la nueva cantidad es menor, devolver la diferencia
        } else if (updatePurchase.newAmountToBuy() < currentAmount) {
            int difference = currentAmount - updatePurchase.newAmountToBuy();
            //devolver al stock
            travelBundle.increaseAvailableBundles(difference);
        }
        //actualizar la cantidad seleccionada
        travelBundle.setAmountToBuy(updatePurchase.newAmountToBuy());
        //recalcular el precio total
        purchase.updateTotalPrice();
        purchaseRepository.save(purchase);
        return  purchaseUtils.convertToPurchaseDto(purchase);
    }

    //  ELIMINAR  compra entera  o VACIAR CARRITO
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
        for (TravelBundle travelBundle : travelBundles) {
            //aumentar en el inventario
            travelBundle.increaseAvailableBundles(travelBundle.getAmountToBuy());
            travelBundleRepository.save(travelBundle);
        }
        // Eliminar la relación de los paquetes con la compra
        purchase.setTravelBundles(new ArrayList<>());
        purchaseRepository.save(purchase);
        purchaseRepository.deleteById(purchaseId);

    }
    //Pasar estado a CANCELADO (despues de estar pending un cierto tienpo?


}

