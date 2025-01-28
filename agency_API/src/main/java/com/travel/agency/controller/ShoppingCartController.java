
package com.travel.agency.controller;

import com.travel.agency.service.ShoppingCartService;
import com.travel.agency.utils.JwtUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final JwtUtil jwtUtil;

    public ShoppingCartController(ShoppingCartService shoppingCartService, JwtUtil jwtUtil) {
        this.shoppingCartService = shoppingCartService;
        this.jwtUtil = jwtUtil;
    }
    
}
