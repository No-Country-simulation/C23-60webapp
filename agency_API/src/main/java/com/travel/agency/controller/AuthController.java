package com.travel.agency.controller;


import com.travel.agency.model.DTO.user.GeneratedTokenDTO;
import com.travel.agency.model.DTO.user.UserLoginDTO;
import com.travel.agency.model.DTO.user.UserRegisterDTO;
import com.travel.agency.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService userService) {
        this.authService = userService;
    }

    @PostMapping("/login")
        public ResponseEntity<GeneratedTokenDTO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        String jwtToken = this.authService.verify(userLoginDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new GeneratedTokenDTO(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        this.authService.register(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
