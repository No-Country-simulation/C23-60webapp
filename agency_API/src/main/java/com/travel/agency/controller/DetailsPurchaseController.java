package com.travel.agency.controller;

import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseDTO;
import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseRequestDTO;
import com.travel.agency.model.DTO.TravelBundle.TravelBundleDTO;
import com.travel.agency.model.DTO.TravelBundle.TravelBundleRequestDTO;
import com.travel.agency.service.DetailsPurchaseService;
import com.travel.agency.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/details")
public class DetailsPurchaseController {

    private final DetailsPurchaseService detailsPurchaseService;
    private final JwtUtil jwtUtil;

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
