package com.travel.agency.controller;

import com.travel.agency.model.DTO.TravelBundle.TravelBundleRequestDTO;
import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.DTO.user.UpdateUserRolesDTO;
import com.travel.agency.service.ImageService;
import com.travel.agency.service.PurchaseService;
import com.travel.agency.service.TravelBundleService;
import com.travel.agency.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PurchaseService purchaseService;
    private final TravelBundleService travelBundleService;
    private final ImageService imageService;

    @Autowired
    public AdminController(UserService userService, PurchaseService purchaseService, TravelBundleService travelBundleService, ImageService imageService) {
        this.userService = userService;
        this.purchaseService = purchaseService;
        this.travelBundleService = travelBundleService;
        this.imageService = imageService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(this.userService.users());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateRoles(@PathVariable Long id, @RequestBody @Valid UpdateUserRolesDTO updateUserRolesDTO) {
        this.userService.updateUserRoles(updateUserRolesDTO, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/purchases")
    public ResponseEntity<List<PurchaseDTO>> getAllPurchaseController() {
        List<PurchaseDTO> purchasesDTOs = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchasesDTOs);
    }

    @PostMapping(value = "/travel-bundles/create-travel-bundle", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createTravelBundle(@RequestPart @Valid TravelBundleRequestDTO travelBundleRequestDTO,
                                                @RequestPart List<MultipartFile> images) {
        this.travelBundleService.createTravelBundle(travelBundleRequestDTO, images);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/images/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        this.imageService.deteleImage(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
