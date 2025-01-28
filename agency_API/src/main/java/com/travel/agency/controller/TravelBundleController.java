package com.travel.agency.controller;

import com.travel.agency.model.DTO.TravelBundle.*;
import com.travel.agency.service.TravelBundleService;
import com.travel.agency.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travel-bundle")
public class TravelBundleController {

    private final TravelBundleService travelBundleService;
    private final JwtUtil jwtUtil;

    public TravelBundleController(TravelBundleService travelBundleService, JwtUtil jwtUtil) {
        this.travelBundleService = travelBundleService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTravelBundle(
            @PathVariable Long id) {
        TravelBundleDTO travelBundleDTO = travelBundleService.getTravelBundle(id);
        if (travelBundleDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(travelBundleDTO);

    }

    @PostMapping("/create")
    public ResponseEntity<?> createTravelBundle(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody TravelBundleRequestDTO travelBundleRequestDTO
    ) {
        String userName = jwtUtil.extractUsername(token);
        TravelBundleDTO travelBundleDTO = travelBundleService.createTravelBundle(travelBundleRequestDTO,userName);
        return ResponseEntity.ok(travelBundleDTO);
    }

}
