package com.travel.agency.controller;
/*
import com.travel.agency.model.DTO.purchase.CartRequest;
import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.DTO.purchase.UpdatePurchase;
import com.travel.agency.service.PurchaseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
*/

import com.travel.agency.model.DTO.ErrorResponseDTO;
import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.entities.ShoppingCart;
import com.travel.agency.service.PurchaseService;
import com.travel.agency.utils.JwtUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final JwtUtil jwtUtil;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, JwtUtil jwtUtil) {
        this.purchaseService = purchaseService;
        this.jwtUtil = jwtUtil;
    }

    //CREAR compra
    @PostMapping("/create")
    public ResponseEntity<?> createPurchaseController(
            @RequestHeader("Authotization") String token,
            @Valid @RequestBody ShoppingCart shoppingCart) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            PurchaseDTO purchaseDTO = purchaseService.createPurchase(shoppingCart, username);
            return ResponseEntity.ok(purchaseDTO);
            //ver Excepciones
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), e.getClass().getSimpleName()));
        }
    }

    //FINALIZAR COMPRA
//    @PostMapping("/finalize/{idPurchase}")
//    public ResponseEntity<PurchaseDTO> finalizePurchaseController(@PathVariable Long idPurchase,
//                                                                  @RequestParam String paymentMethod) {
//        PurchaseDTO purchaseDTO = purchaseService.finalizePurchase(idPurchase, paymentMethod);
//        return ResponseEntity.ok(purchaseDTO);
//
//    }

    //aCTUALIZAR
//    @PutMapping("/cart/update/{purchaseId}")
//    public ResponseEntity<PurchaseDTO> updatePurchaseController(@PathVariable Long purchaseId,
//                                                                @RequestBody UpdatePurchase updatePurchase) {
//        PurchaseDTO purchaseDTO = purchaseService.updatePurchase(purchaseId, updatePurchase);
//        return ResponseEntity.ok(purchaseDTO);
//    }

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
//    @GetMapping("/user/{username}")
//    public ResponseEntity<List<PurchaseDTO>> getPurchasesByUsernameController(@PathVariable String username) {
//        List<PurchaseDTO> purchaseDTOs = purchaseService.searchPurchaseByUser(username);
//        return ResponseEntity.ok(purchaseDTOs);
//    }

    //Eliminar compra por ID
    @Transactional
    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<Void> deletePurchaseController(@PathVariable Long purchaseId) {
        purchaseService.deletePurchaseById(purchaseId);
        return ResponseEntity.noContent().build();
    }


}
