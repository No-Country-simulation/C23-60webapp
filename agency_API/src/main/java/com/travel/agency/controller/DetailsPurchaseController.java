package com.travel.agency.controller;

import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseDTO;
import com.travel.agency.service.DetailsPurchaseService;
import com.travel.agency.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detailsPurchases")
@CrossOrigin(origins = "http://localhost:5173/")
public class DetailsPurchaseController {

    private final DetailsPurchaseService detailsPurchaseService;
    private final JwtService jwtService;

    @Autowired
    public DetailsPurchaseController(DetailsPurchaseService detailsPurchaseService, JwtService jwtService) {
        this.detailsPurchaseService = detailsPurchaseService;
        this.jwtService = jwtService;
    }


    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<List<DetailsPurchaseDTO>> getDetailsByPurchaseIdController(
            @PathVariable Long purchaseId,
            @RequestHeader("Authorization") String token) {
        String username = jwtService.extractUsername(token);
        List<DetailsPurchaseDTO> detailsPurchase = detailsPurchaseService.getDetailsByPurchaseId(purchaseId, username);
        return ResponseEntity.ok(detailsPurchase);
    }

    @GetMapping("/{detailId}")
    public ResponseEntity<DetailsPurchaseDTO> getDetailById(
            @PathVariable Long detailId,
            @RequestHeader("Authorization") String token) {

        String username = jwtService.extractUsername(token);
        DetailsPurchaseDTO detailsPurchase = detailsPurchaseService.getDetailById(detailId, username);
        return ResponseEntity.ok(detailsPurchase);
    }

}
