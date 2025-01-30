package com.travel.agency.service;

import com.travel.agency.model.DTO.user.UpdateUserDTO;
import com.travel.agency.model.DTO.user.UpdateUserRolesDTO;
import com.travel.agency.model.DTO.user.UserDTO;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.UserRepository;
import com.travel.agency.utils.MapperUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;


    @Autowired
    public UserService(UserRepository userRepository, AuthService authService){this.userRepository = userRepository;
        this.authService = authService;
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
        if(!user.equals(this.authService.getUser())){
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
        if(!user.equals(this.authService.getUser())){
            throw new AccessDeniedException("You are not authorized to delete this user");
        }else{
        this.userRepository.delete(user);
        }
    }
/*
    //Metodo que regresa el usuario logueado en un momento dado.
    public User getUserInSession(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
    }
*/
}
