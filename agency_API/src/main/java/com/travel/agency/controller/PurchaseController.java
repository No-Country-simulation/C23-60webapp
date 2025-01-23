package com.travel.agency.controller;

import com.travel.agency.model.DTO.purchase.CartRequest;
import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.entities.Purchase;
import com.travel.agency.service.PurchaseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }


    //CREAR compra
    @PostMapping("/cart")
    public ResponseEntity<PurchaseDTO> addTravelBundleToCart(@RequestBody CartRequest cartRequest) {
        try {
            Purchase updatePurchase = purchaseService.addTravelBundleToCart(cartRequest);
            //convertir a DTO
            PurchaseDTO purchaseDTO = new PurchaseDTO(updatePurchase);
            return ResponseEntity.ok(purchaseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //FINALIZAR COMPRA
    @PostMapping("/finalize/{idPurchase}")
    public ResponseEntity<PurchaseDTO> finalizePurchaseController(@PathVariable Long idPurchase,
                                                                  @RequestParam String paymentMethod) {
        PurchaseDTO purchaseDTO = purchaseService.finalizePurchase(idPurchase, paymentMethod);
        return ResponseEntity.ok(purchaseDTO);

    }

    //Ver todas las compras, ADMIN?
    @GetMapping
    public ResponseEntity<List<PurchaseDTO>> getAllPurchaseController() {
        List<PurchaseDTO> purchaseDTOs = purchaseService.getAllPurchases()
                .stream()
                .map(PurchaseDTO::new) // Convertir cada Purchase a PurchaseDTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(purchaseDTOs);
    }

    //ver compra por ID
    @GetMapping("/{idPurchase}")
    public PurchaseDTO findPurchaseByIdController(@PathVariable Long idPurchase) {
        Purchase purchaseFinded = purchaseService.searchPurchaseById(idPurchase);
        PurchaseDTO purchaseDTO = new PurchaseDTO(purchaseFinded);
        return purchaseDTO;
    }

    //ver compras por usuario / por estado?


    //actualizar compra

    //Eliminar compra por ID
    @Transactional
    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<Void> deletePurchaseController(@PathVariable Long purchaseId) {
        purchaseService.deletePurchaseById(purchaseId);
        return ResponseEntity.noContent().build();
    }


}
