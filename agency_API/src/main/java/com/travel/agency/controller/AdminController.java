package com.travel.agency.controller;

import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.DTO.user.UpdateUserRolesDTO;
import com.travel.agency.service.PurchaseService;
import com.travel.agency.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PurchaseService purchaseService;

    @Autowired
    public AdminController(UserService userService, PurchaseService purchaseService) {
        this.userService = userService;
        this.purchaseService = purchaseService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(this.userService.users());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateRoles(@PathVariable Long id, @RequestBody @Valid UpdateUserRolesDTO updateUserRolesDTO){
        this.userService.updateUserRoles(updateUserRolesDTO,id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/purchases")
    public ResponseEntity<List<PurchaseDTO>> getAllPurchaseController() {
        List<PurchaseDTO> purchasesDTOs = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchasesDTOs);
    }

}
