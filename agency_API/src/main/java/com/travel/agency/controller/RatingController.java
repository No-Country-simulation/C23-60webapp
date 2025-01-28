package com.travel.agency.controller;

import com.travel.agency.model.DTO.rating.CreateRatingDTO;
import com.travel.agency.model.DTO.rating.RatingDTO;
import com.travel.agency.service.RatingService;
import com.travel.agency.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;
    private final JwtUtil jwtUtil;

    public RatingController(JwtUtil jwtUtil, RatingService ratingService) {
        this.jwtUtil = jwtUtil;
        this.ratingService = ratingService;
    }

    //dar(crear) rating
    @PostMapping("/give-rating")
    public ResponseEntity<?> createRatingController(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody CreateRatingDTO createRatingDTOO) {
        String username = jwtUtil.extractUsername(token);
        RatingDTO createdRating = ratingService.createRating(createRatingDTOO, username);
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
    public ResponseEntity<List<RatingDTO>> getPurchasesByUsernameController(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token);
        List<RatingDTO> ratingDTOList = ratingService.searchRatingsByUser(username);
        return ResponseEntity.ok(ratingDTOList);
    }
    //ver por paquete

    //actualizar?

    //eliminar
}