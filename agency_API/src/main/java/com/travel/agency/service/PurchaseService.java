package com.travel.agency.service;

import com.travel.agency.enums.Status;
import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.model.entities.Purchase;
import com.travel.agency.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;

    //1 - CREAR compra
    //2 - Ver todas las compras
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    //3 - Ver compra por ID
    public Purchase searchPurchaseById(Long idPurchase) {
        Optional<Purchase> purchase = purchaseRepository.findById(idPurchase);
        return purchase.orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", idPurchase));
    }

    //4 - ACTUALIZAR COMPRA (VER STATUS, TIENE QUE E3STAR PENDING

    // 5 - ELIMINAR  compra  o cambiar estado a CANCELADO?
    public void deletePurchaseById(Long idPurchase) throws ResourceNotFoundException {
        //Existe la compra con ese ID?
        Purchase purchase = purchaseRepository.findById(idPurchase).orElseThrow(() -> new ResourceNotFoundException("Purchase", "ID", idPurchase));
        //verificar antes de eliminar que todavia este pendiente de pago
        if (purchase.getStatus() != Status.PENDING) {
            //Operacion NO valida en el estado actual
            throw new IllegalMonitorStateException("Cannot delete a precessed purchase");
        }
        purchaseRepository.deleteById(idPurchase);
    }

    //6 - Pasar estado a CANCELADO (despues de estar pending un cierto tienpo?) Y/O si el usuario la cancela

}

