package com.travel.agency.controller;

import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseDTO;
import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseRequestDTO;
import com.travel.agency.service.DetailsPurchaseService;
import com.travel.agency.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/details")
public class DetailsPurchaseController {

    private final DetailsPurchaseService detailsPurchaseService;
    private final JwtUtil jwtUtil;

    @Autowired
    public DetailsPurchaseController(DetailsPurchaseService detailsPurchaseService, JwtUtil jwtUtil) {
        this.detailsPurchaseService = detailsPurchaseService;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> createTravelBundle(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody DetailsPurchaseRequestDTO detailsPurchaseRequestDTO
    ) {
        String userName = jwtUtil.extractUsername(token);
        DetailsPurchaseDTO detailsPurchaseDTO = detailsPurchaseService.addTravelBundle(detailsPurchaseRequestDTO,userName);
        return ResponseEntity.ok(detailsPurchaseDTO);
    }

}
