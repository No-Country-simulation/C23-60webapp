package com.travel.agency.service;

import com.travel.agency.enums.Role;
import com.travel.agency.model.DTO.user.UserLoginDTO;
import com.travel.agency.model.DTO.user.UserRegisterDTO;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.UserRepository;
import com.travel.agency.utils.JwtUtil;
import com.travel.agency.utils.MapperUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final ShoppingCartService ShoppingCartService;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService, @Lazy ShoppingCartService shoppingCartService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        ShoppingCartService = shoppingCartService;
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new IllegalStateException("No authenticated user found");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDetails.getUsername()));
    }

    //Metodo registro chequea si el admin no esta registrado en db.
    @Transactional
    public void register(UserRegisterDTO userRegisterDTO) {
        User user = MapperUtil.toEntity(userRegisterDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<User> isAdminRegistered = userRepository.findByEmail("admin@gmail.com");
        if (!isAdminRegistered.isPresent() && userRegisterDTO.email().equals("admin@gmail.com")) {
            user.setRoles(Set.of(Role.ADMIN));
        } else {
            user.setRoles(Set.of(Role.USER));
        }
        user.setRegisterDate(LocalDate.now());
        this.userRepository
                .save(user);
        ShoppingCartService.createShoppingCart(user);
    }

    //Verify method authenticates the User and creates a JWT TOKEN.
    public String verify(@Valid UserLoginDTO userLoginDTO) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.email(), userLoginDTO.password())
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("User does not exists or credentials are wrong");
        }
        return this.jwtUtil.generateToken(this.userDetailsService.loadUserByUsername(userLoginDTO.email()));
    }
}
