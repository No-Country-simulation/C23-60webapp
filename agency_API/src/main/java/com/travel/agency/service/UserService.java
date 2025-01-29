package com.travel.agency.service;

import com.travel.agency.enums.Role;
import com.travel.agency.model.DTO.user.*;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.UserRepository;
import com.travel.agency.utils.JwtUtil;
import com.travel.agency.utils.MapperUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final ShoppingCartService ShoppingCartService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService, ShoppingCartService ShoppingCartService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.ShoppingCartService = ShoppingCartService;
    }


    //Metodo registro chequea si el admin no esta registrado en db.
    @Transactional
    public void register(UserRegisterDTO userRegisterDTO) {
        User user = MapperUtil.toEntity(userRegisterDTO);
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

    public List<UserDTO> users() {
        List<User> users = this.userRepository.findAll();
        return MapperUtil.toDTO(users);
    }

    @Transactional
    public void updateUserRoles(UpdateUserRolesDTO updateUserRolesDTO, Long id){
        User user = this.userRepository.findById(id).orElseThrow();
        user.setRoles(updateUserRolesDTO.roles());
        this.userRepository.save(user);
    }

    @Transactional
    public void updateUserDetails(UpdateUserDTO updateUserDTO, Long id){
        User user = this.userRepository.findById(id).orElseThrow();
        if(!user.equals(getUserInSession())){
            throw new AccessDeniedException("You are not authorized to update this user");
        }else{
            Optional.ofNullable(updateUserDTO.firstName())
                    .ifPresent(user::setFirstName);
            Optional.ofNullable(updateUserDTO.lastName())
                    .ifPresent(user::setLastName);
            Optional.ofNullable(updateUserDTO.identityCard())
                    .ifPresent(user::setIdentityCard);
            Optional.ofNullable(updateUserDTO.email())
                    .ifPresent(user::setEmail);
            Optional.ofNullable(updateUserDTO.username())
                    .ifPresent(user::setUsername);
            Optional.ofNullable(updateUserDTO.phoneNumber())
                    .ifPresent(user::setPhoneNumber);
        }
        this.userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id){
        User user = this.userRepository.findById(id).orElseThrow();
        if(!user.equals(getUserInSession())){
            throw new AccessDeniedException("You are not authorized to delete this user");
        }else{
        this.userRepository.delete(user);
        }
    }

    //Metodo que regresa el usuario logueado en un momento dado.
    public User getUserInSession(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
    }

}
