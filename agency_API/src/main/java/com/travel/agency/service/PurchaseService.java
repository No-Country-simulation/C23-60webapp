package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.mapper.DetailsPurchaseMapper;
import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseRequestDTO;
import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.entities.*;
import com.travel.agency.repository.PurchaseRepository;
import com.travel.agency.repository.ShoppingCartRepository;
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
    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    //CREAR COMPRA O "FACTURA DE COMPRA"
    @Transactional
    public PurchaseDTO createPurchase(ShoppingCart shoppingCart, String username){




    }

    // Finalizar compra activa, cambiando estado a COMPLETED
//    @Transactional
//    public PurchaseDTO finalizePurchase(Long idPurchase, String paymentMethod) {
//        Purchase purchase = purchaseRepository.findById(idPurchase)
//                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", idPurchase));
//
//        if (purchase.getStatus() != Status.PENDING) {
//            throw new IllegalStateException("Purchase isn´t in PENDING status and cannot be finalized");
//        }
//        //Setear la forma de pago y cambiar el estado a confirmed
//        purchase.setPaymentMethod(paymentMethod);
//        purchase.setStatus(Status.CONFIRMED);
//
//        //Guardar en la base de datos
//        Purchase updatedPurchase = purchaseRepository.save(purchase);
//        //convierto a DTO
//        return purchaseUtils.convertToPurchaseDto(updatedPurchase);
//    }

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
//    public List<PurchaseDTO> searchPurchaseByUser(String username) {
//        Optional<User> user = userRepository.findByUsername(username);
//        if (user.isEmpty() || user.get().getPurchases() == null) {
//            throw new RuntimeException("User not found or no purchases available");
//        }
//        List<Purchase> purchaseList = user.get().getPurchases();
//        return purchaseUtils.purchaseMapperDto(purchaseList);
//
//    }

    //ACTUALIZAR COMPRA (VER STATUS, TIENE QUE E3STAR PENDING)
//    @Transactional
//    public PurchaseDTO updatePurchase(Long purchaseId, UpdatePurchase updatePurchase) {
//        //Existe la compra con ese ID?
//        Purchase purchase = purchaseRepository.findById(purchaseId)
//                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", purchaseId));
//        //verificar antes de eliminar que todavia este pendiente de pago
//        if (purchase.getStatus() != Status.PENDING) {
//            throw new IllegalStateException("Only purchases in PENDING status can be modified");
//        }
//
//        //Buscar el paquete dentro de la compra
//        TravelBundle travelBundle = purchase.getTravelBundles().stream()
//                .filter(tb -> tb.getId().equals(updatePurchase.travelBundleId()))
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Travel bundle", "ID", updatePurchase.travelBundleId()));
//        // Validar que la nueva cantidad sea válida (mayor que 0)
//        if (updatePurchase.newAmountToBuy() <= 0) {
//            throw new IllegalArgumentException("Amount to buy must be greater than 0");
//        }
//        //obtener cantidad actual
//        int currentAmount = travelBundle.getAmountToBuy();
//        //si la nueva cantidad es mayor, restar del stock disponible
//        if (updatePurchase.newAmountToBuy() > currentAmount) {
//            int difference = updatePurchase.newAmountToBuy() - currentAmount;
//            //resto del stock
//            travelBundle.decreaseAvaliableBundles(difference);
//            //si la nueva cantidad es menor, devolver la diferencia
//        } else if (updatePurchase.newAmountToBuy() < currentAmount) {
//            int difference = currentAmount - updatePurchase.newAmountToBuy();
//            //devolver al stock
//            travelBundle.increaseAvailableBundles(difference);
//        }
//        //actualizar la cantidad seleccionada
//        travelBundle.setAmountToBuy(updatePurchase.newAmountToBuy());
//        //recalcular el precio total
//        purchase.updateTotalPrice();
//        //guardar en la compra actual
//        purchaseRepository.save(purchase);
//        //Coonvierto a dto y retorno
//        return  purchaseUtils.convertToPurchaseDto(purchase);
//    }

    //  ELIMINAR  compra entera  o VACIAR CARRITO
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

