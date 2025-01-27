package com.travel.agency.service;

import com.travel.agency.enums.Role;
import com.travel.agency.model.DTO.user.UserDTO;
import com.travel.agency.model.DTO.user.UserLoginDTO;
import com.travel.agency.model.DTO.user.UserRegisterDTO;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.UserRepository;
import com.travel.agency.utils.JwtUtil;
import com.travel.agency.utils.MapperUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }


    //implementing the register method
    @Transactional
    public void register(UserRegisterDTO userRegisterDTO) {
        User user = new User(
                userRegisterDTO.firstName(),
                userRegisterDTO.lastName(),
                Integer.parseInt(userRegisterDTO.identityCard()),
                userRegisterDTO.email(),
                userRegisterDTO.email(),
                userRegisterDTO.password()
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<User> isAdminRegistered = userRepository.findByEmail("admin@gmail.com");
        if(!isAdminRegistered.isPresent() && userRegisterDTO.email().equals("admin@gmail.com")){
            user.setRoles(Set.of(Role.ADMIN));
        }else {
            user.setRoles(Set.of(Role.USER));
        }
        user.setRegisterDate(LocalDate.now());
        this.userRepository
                .save(user);
    }

    //Verify method authenticates the User and creates a JWT TOKEN.
    //I need to create a custom exception error and handle it.
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

    public List<UserDTO> users() {
        List<User> users = this.userRepository.findAll();
        return users.stream().map(user ->
                MapperUtil.mapperEntity(user1 ->
                        new UserDTO(
                                user.getId(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getIdentityCard(),
                                user.getEmail(),
                                user.getUsername(),
                                user.getPhoneNumber(),
                                user.getRegisterDate()
                        ))).toList();
    }
}
