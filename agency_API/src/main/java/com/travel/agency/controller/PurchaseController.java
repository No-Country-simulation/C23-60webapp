package com.travel.agency.controller;

import com.travel.agency.model.DTO.purchase.CartRequest;
import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.DTO.purchase.UpdatePurchase;
import com.travel.agency.service.PurchaseService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            PurchaseDTO purchaseDTO = purchaseService.addTravelBundleToCart(cartRequest);
            //convertir a DTO
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

    //aCTUALIZAR
    @PutMapping("/cart/update/{purchaseId}")
    public ResponseEntity<PurchaseDTO> updatePurchaseController(@PathVariable Long purchaseId,
                                                                @RequestBody @Valid UpdatePurchase updatePurchase) {
        PurchaseDTO purchaseDTO = purchaseService.updatePurchase(purchaseId, updatePurchase);
        return ResponseEntity.ok(purchaseDTO);
    }


    //Ver todas las compras, ADMIN?
    @GetMapping("")
    public ResponseEntity<List<PurchaseDTO>> getAllPurchaseController() {
        List<PurchaseDTO> purchasesDTOs = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchasesDTOs);
    }

    //ver compra por ID
    @GetMapping("/{idPurchase}")
    public PurchaseDTO searchPurchaseByIdController(@PathVariable Long idPurchase) {
        return purchaseService.searchPurchaseById(idPurchase);

    }

    //ver compra por usuario
    @GetMapping("/user/{username}")
    public ResponseEntity<List<PurchaseDTO>> getPurchasesByUsernameController(@PathVariable String username) {
        List<PurchaseDTO> purchaseDTOs = purchaseService.searchPurchaseByUser(username);
        return ResponseEntity.ok(purchaseDTOs);
    }

    //Eliminar compra por ID
    @Transactional
    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<Void> deletePurchaseController(@PathVariable Long purchaseId) {
        purchaseService.deletePurchaseById(purchaseId);
        return ResponseEntity.noContent().build();
    }

    //Para obtener compras canceladas
    @GetMapping("/cancelled")
    public List<PurchaseDTO> getCancelledPurchases() {
        return purchaseService.getCancelledPurchases();
    }


}
