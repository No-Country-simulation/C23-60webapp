package com.travel.agency.controller;

import com.travel.agency.model.DTO.ShoppingCart.AddToShoppingCartDTO;
import com.travel.agency.model.DTO.ShoppingCart.ShoppingCartDTO;
import com.travel.agency.service.ShoppingCartService;
import com.travel.agency.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final JwtUtil jwtUtil;

    public ShoppingCartController(ShoppingCartService shoppingCartService, JwtUtil jwtUtil) {
        this.shoppingCartService = shoppingCartService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<?> getShoppingCart() {                     
        ShoppingCartDTO shoppingCartDTO = shoppingCartService.getShoppingCart();
        if (shoppingCartDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shoppingCartDTO);
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> AddToShoppingCart(@Valid @RequestBody AddToShoppingCartDTO addToShoppingCartDTO) {                     
        ShoppingCartDTO shoppingCartDTO = shoppingCartService.AddToShoppingCart(addToShoppingCartDTO);
        if (shoppingCartDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shoppingCartDTO);
    }

}
