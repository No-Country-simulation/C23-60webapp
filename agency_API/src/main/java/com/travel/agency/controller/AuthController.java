package com.travel.agency.controller;


import com.travel.agency.model.DTO.user.GeneratedTokenDTO;
import com.travel.agency.model.DTO.user.UserLoginDTO;
import com.travel.agency.model.DTO.user.UserRegisterDTO;
import com.travel.agency.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
        public ResponseEntity<GeneratedTokenDTO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        String jwtToken = this.userService.verify(userLoginDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new GeneratedTokenDTO(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        this.userService.register(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

}
