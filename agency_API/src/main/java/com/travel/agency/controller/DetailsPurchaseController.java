package com.travel.agency.controller;

import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseDTO;
import com.travel.agency.service.DetailsPurchaseService;
import com.travel.agency.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detailsPurchases")
public class DetailsPurchaseController {

    private final DetailsPurchaseService detailsPurchaseService;
    private final JwtUtil jwtUtil;

    @Autowired
    public DetailsPurchaseController(DetailsPurchaseService detailsPurchaseService, JwtUtil jwtUtil) {
        this.detailsPurchaseService = detailsPurchaseService;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<List<DetailsPurchaseDTO>> getDetailsByPurchaseIdController(
            @PathVariable Long purchaseId,
            @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token);
        List<DetailsPurchaseDTO> detailsPurchase = detailsPurchaseService.getDetailsByPurchaseId(purchaseId, username);
        return ResponseEntity.ok(detailsPurchase);
    }

    @GetMapping("/{detailId}")
    public ResponseEntity<DetailsPurchaseDTO> getDetailById(
            @PathVariable Long detailId,
            @RequestHeader("Authorization") String token) {

        String username = jwtUtil.extractUsername(token);
        DetailsPurchaseDTO detailsPurchase = detailsPurchaseService.getDetailById(detailId, username);
        return ResponseEntity.ok(detailsPurchase);
    }

}
