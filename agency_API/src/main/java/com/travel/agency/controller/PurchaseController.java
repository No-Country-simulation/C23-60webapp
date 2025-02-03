package com.travel.agency.controller;

import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.service.PurchaseService;
import com.travel.agency.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final JwtService jwtService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, JwtService jwtService) {
        this.purchaseService = purchaseService;
        this.jwtService = jwtService;
    }

    @PostMapping("/buy")
    public ResponseEntity<?> createPurchaseController(
            @RequestHeader("Authorization") String token) {
        String username = jwtService.extractUsername(token);
        PurchaseDTO purchaseDTO = purchaseService.createPurchase(username);
        return ResponseEntity.ok(purchaseDTO);
    }

    @GetMapping("")
    public ResponseEntity<List<PurchaseDTO>> getAllPurchasesController() {
        List<PurchaseDTO> purchaseDTOList = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchaseDTOList);
    }

    @GetMapping("/{idPurchase}")
    public ResponseEntity<PurchaseDTO> searchPurchaseByIdController(@PathVariable Long idPurchase) {
        PurchaseDTO purchaseDTO = purchaseService.searchPurchaseById(idPurchase);
        return ResponseEntity.ok(purchaseDTO);
    }

    @GetMapping("/user-purchases")
    public ResponseEntity<?> getPurchasesByUsernameController(@RequestHeader("Authorization") String token) {
        String username = jwtService.extractUsername(token);
        List<PurchaseDTO> userPurchases = purchaseService.searchPurchaseByUser(username);
        return ResponseEntity.ok(userPurchases);
    }

    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<Void> deletePurchaseController(@PathVariable Long purchaseId) {
        purchaseService.deletePurchaseById(purchaseId);
        return ResponseEntity.noContent().build();
    }


}
