package com.travel.agency.controller;

import com.travel.agency.model.DTO.TravelBundle.*;
import com.travel.agency.service.TravelBundleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
@RestController
@RequestMapping("/travel-bundle")
public class TravelBundleController {

    private final TravelBundleService travelBundleService;

    public TravelBundleController(TravelBundleService travelBundleService) {
        this.travelBundleService = travelBundleService;
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

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.travelBundleService.getAllTravelBundles());
    }

}
