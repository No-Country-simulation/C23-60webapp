package com.travel.agency.controller;

import com.travel.agency.model.DTO.ShoppingCart.AddToShoppingCartDTO;
import com.travel.agency.model.DTO.ShoppingCart.ShoppingCartDTO;
import com.travel.agency.model.DTO.ShoppingCart.UpdateDetailsShoppingCartDTO;
import com.travel.agency.service.ShoppingCartService;
import com.travel.agency.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDetailShoppingCart(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDetailsShoppingCartDTO dto) {
        ShoppingCartDTO shoppingCartDTO = shoppingCartService.updateDetailShoppingCart(id,dto);
        if (shoppingCartDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shoppingCartDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFromShoppingCart(
            @PathVariable Long id) {
        ShoppingCartDTO shoppingCartDTO = shoppingCartService.deleteFromShoppingCart(id);
        if (shoppingCartDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shoppingCartDTO);
    }
    
    @PutMapping("/clean")
    public ResponseEntity<?> cleanShoppingCart() {
        ShoppingCartDTO shoppingCartDTO = shoppingCartService.clearShoppingCart();
        if (shoppingCartDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shoppingCartDTO);
    }
}
