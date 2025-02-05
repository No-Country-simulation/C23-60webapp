package com.travel.agency.controller;

import com.travel.agency.model.DTO.rating.CreateRatingDTO;
import com.travel.agency.model.DTO.rating.RatingDTO;
import com.travel.agency.model.DTO.rating.UpdateRating;
import com.travel.agency.service.RatingService;
import com.travel.agency.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;
    private final JwtService jwtService;

    public RatingController(JwtService jwtService, RatingService ratingService) {
        this.jwtService = jwtService;
        this.ratingService = ratingService;
    }

    //dar(crear) rating
    @PostMapping("/give-rating")
    public ResponseEntity<?> createRatingController(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody CreateRatingDTO createRatingDTO) {
        String username = jwtService.extractUsername(token);
        RatingDTO createdRating = ratingService.createRating(createRatingDTO, username);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    //ver por id
    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingDTO> getRatingByIdController(@PathVariable Long ratingId) {
        RatingDTO ratingDTO = ratingService.searchRatingByID(ratingId);
        return ResponseEntity.ok(ratingDTO);
    }

    //ver todas
    @GetMapping("")
    public ResponseEntity<List<RatingDTO>> getAllRatingsController() {
        List<RatingDTO> ratingDTOList = ratingService.getAllRatings();
        return ResponseEntity.ok(ratingDTOList);
    }

    //ver por usuario
    @GetMapping("/user-ratings")
    public ResponseEntity<List<RatingDTO>> getRatingByUsernameController(@RequestHeader("Authorization") String token) {
        String username = jwtService.extractUsername(token);
        List<RatingDTO> ratingDTOList = ratingService.searchRatingsByUser(username);
        return ResponseEntity.ok(ratingDTOList);
    }

    //ver por paquete
    @GetMapping("travelBundle/{travelBundleId}")
    public ResponseEntity<List<RatingDTO>> getRatingByTravelBundleController(@PathVariable Long travelBundleId) {
        List<RatingDTO> ratingDTOList = ratingService.getRatingByTravelBundle(travelBundleId);
        return ResponseEntity.ok(ratingDTOList);
    }

    //actualizar?
    @PutMapping("/update")
    public ResponseEntity<RatingDTO> updateRatingController(@RequestHeader("Authorization") String token, @RequestBody @Valid UpdateRating updateRating) {
        String username = jwtService.extractUsername(token);
        RatingDTO createdRating = ratingService.updateRating(updateRating, username);
        return new ResponseEntity<>(createdRating, HttpStatus.ACCEPTED);
    }

    //eliminar
    @DeleteMapping("{ratingId}")
    public ResponseEntity<Void> deleteRatingController(@PathVariable Long ratingId) {
        ratingService.deleteRatingByIc(ratingId);
        return ResponseEntity.noContent().build();
    }
}