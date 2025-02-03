package com.travel.agency.controller;

import com.travel.agency.model.DTO.TravelBundle.*;
import com.travel.agency.service.TravelBundleService;
import com.travel.agency.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travel-bundle")
public class TravelBundleController {

    private final TravelBundleService travelBundleService;
    private final JwtService jwtService;

    public TravelBundleController(TravelBundleService travelBundleService, JwtService jwtService) {
        this.travelBundleService = travelBundleService;
        this.jwtService = jwtService;
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
/*
    @PostMapping("/create")
    public ResponseEntity<?> createTravelBundle(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody TravelBundleRequestDTO travelBundleRequestDTO
    ) {
        String userName = jwtUtil.extractUsername(token);
        TravelBundleDTO travelBundleDTO = travelBundleService.createTravelBundle(travelBundleRequestDTO,userName);
        return ResponseEntity.ok(travelBundleDTO);
    }*/

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.travelBundleService.getAllTravelBundles());
    }

}
