package com.travel.agency.controller;

import com.travel.agency.model.entities.Purchase;
import com.travel.agency.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;


    //CREAR compra

    //Ver todas las compras, ADMIN?
    @GetMapping
    public ResponseEntity<List<Purchase>> getAllPurchaseController() {
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }

    @GetMapping("/{idPurchase}")
    public Purchase findPurchaseByIdController(@PathVariable Long idPurchase) {
        return purchaseService.searchPurchaseById(idPurchase);
    }


    //actualizar compra

    @DeleteMapping("/{idPurchase}")
    public ResponseEntity<Void> deletePurchaseController(@PathVariable Long idPurchase){
        purchaseService.deletePurchaseById(idPurchase);
        return ResponseEntity.noContent().build();
    }




}
