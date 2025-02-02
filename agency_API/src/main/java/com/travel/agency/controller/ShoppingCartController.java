package com.travel.agency.controller;

import com.travel.agency.model.DTO.ShoppingCart.AddToShoppingCartDTO;
import com.travel.agency.model.DTO.ShoppingCart.ShoppingCartDTO;
import com.travel.agency.model.DTO.ShoppingCart.UpdateDetailsShoppingCartDTO;
import com.travel.agency.service.ShoppingCartService;
import com.travel.agency.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final JwtService jwtService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, JwtService jwtService) {
        this.shoppingCartService = shoppingCartService;
        this.jwtService = jwtService;
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
