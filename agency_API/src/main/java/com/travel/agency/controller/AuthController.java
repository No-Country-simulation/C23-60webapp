package com.travel.agency.controller;


import com.travel.agency.model.DTO.user.UserLoginDTO;
import com.travel.agency.model.DTO.user.UserRegisterDTO;
import com.travel.agency.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        String jwtToken = this.userService.verify(userLoginDTO);
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        this.userService.register(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
