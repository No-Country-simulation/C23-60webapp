package com.travel.agency.controller;

import com.travel.agency.model.DTO.ErrorResponseDTO;
import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.service.PurchaseService;
import com.travel.agency.utils.JwtUtil;
import jakarta.transaction.Transactional;
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
            @RequestHeader("Authorization") String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            PurchaseDTO purchaseDTO = purchaseService.createPurchase(username);
            return ResponseEntity.ok(purchaseDTO);
            //ver Excepciones
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), e.getClass().getSimpleName()));
        }
    }



    //ver compra por ID
    @GetMapping("/{idPurchase}")
    public ResponseEntity<PurchaseDTO> searchPurchaseByIdController(@PathVariable Long idPurchase) {
        PurchaseDTO purchaseDTO = purchaseService.searchPurchaseById(idPurchase);
        return ResponseEntity.ok(purchaseDTO);
    }


    //ver compra por usuario
    @GetMapping("/user-purchases")
    public ResponseEntity<?> getPurchasesByUsernameController(@RequestHeader("Authorization") String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            List<PurchaseDTO> userPurchases = purchaseService.searchPurchaseByUser(username);
            return ResponseEntity.ok(userPurchases);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), e.getClass().getSimpleName()));
        }
    }

    //Eliminar compra por ID
    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<Void> deletePurchaseController(@PathVariable Long purchaseId) {
        purchaseService.deletePurchaseById(purchaseId);
        return ResponseEntity.noContent().build();
    }


}
