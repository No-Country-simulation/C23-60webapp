package com.travel.agency.controller;

import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseDTO;
import com.travel.agency.model.entities.DetailsPurchase;
import com.travel.agency.service.DetailsPurchaseService;
import com.travel.agency.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detailsPurchase")
public class DetailsPurchaseController {

    private final DetailsPurchaseService detailsPurchaseService;
    private final JwtUtil jwtUtil;

    @Autowired
    public DetailsPurchaseController(DetailsPurchaseService detailsPurchaseService, JwtUtil jwtUtil) {
        this.detailsPurchaseService = detailsPurchaseService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/transfer/{purchaseId}")
    public ResponseEntity<List<DetailsPurchaseDTO>> transferDetailsToPurchase(
            @PathVariable Long purchaseId,
            @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token);
        List<DetailsPurchaseDTO> detailsPurchase = detailsPurchaseService.createDetailsFromShoppingCart(purchaseId, username);
        return ResponseEntity.ok(detailsPurchase);
    }

}
