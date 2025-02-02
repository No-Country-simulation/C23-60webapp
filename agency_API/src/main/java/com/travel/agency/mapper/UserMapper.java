package com.travel.agency.mapper;

import com.travel.agency.model.DTO.user.UserDTO;
import com.travel.agency.model.DTO.user.UserRegisterDTO;
import com.travel.agency.model.entities.User;

import java.util.List;

public class UserMapper {

    public static User toEntity(UserRegisterDTO userRegisterDTO){
        return new User(
                userRegisterDTO.firstName(),
                userRegisterDTO.lastName(),
                Integer.parseInt(userRegisterDTO.identityCard()),
                userRegisterDTO.email(),
                userRegisterDTO.email(),
                userRegisterDTO.password()
        );
    }

    public static UserDTO toDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getIdentityCard(),
                user.getEmail(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getRegisterDate(),
                user.getRoles()
        );
    }

    public static List<UserDTO> toDTO(List<User> user){
        return user.stream().map(UserMapper::toDTO).toList();
    }

}
