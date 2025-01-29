package com.travel.agency.utils;

import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.DTO.rating.CreateRatingDTO;
import com.travel.agency.model.DTO.rating.RatingDTO;
import com.travel.agency.model.DTO.user.UserDTO;
import com.travel.agency.model.DTO.user.UserRegisterDTO;
import com.travel.agency.model.entities.Purchase;
import com.travel.agency.model.entities.Rating;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.model.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class MapperUtil {

    //La idea es crear 2 metodos: toDTO y toEntity y sobrecargarlos. Cambiarles los parametros
    //y el tipo de retorno. Obviamente, esto no es más que una propuesta mia. Si alguien tiene una idea mejor
    //o les parece correcto hacerlo de la manera tradicional con clases separadas para cada entidad, lo
    //discutimos en una reunion.
    //Si implementamos todos los convertidores en esta clase, este metodo va a quedar obsoleto.
    public static <T, D> D mapperEntity(Function<T, D> converter) {
        return converter.apply(null);
    }

    public static List<PurchaseDTO> purchaseMapperDto(List<Purchase> purchaseList) {
        return purchaseList.stream()
                .map(PurchaseDTO::new)
                .collect(Collectors.toList());
    }

    public static RatingDTO toRatingDTO(Rating rating) {
        return new RatingDTO(
                rating.getId(),
                rating.getUser().getUsername(),
                rating.getRating(),
                rating.getTravelBundle().getId(),
                rating.getComment(),
                rating.getCreationDate()
        );
    }

    public static Rating toRating(CreateRatingDTO createRatingDTO, User user, TravelBundle travelBundle) {
        return new Rating(
                createRatingDTO.rating(),
                user,
                travelBundle,
                createRatingDTO.comment(),
                LocalDateTime.now()
        );
    }
    public static List<RatingDTO> toRatingDtoList(List<Rating> ratingList){
        return ratingList.stream().map(MapperUtil::toRatingDTO)
                .collect(Collectors.toList());
    }

    public static User toEntity(UserRegisterDTO userRegisterDTO){
        return new User(
                userRegisterDTO.firstName(),
                userRegisterDTO.lastName(),
                Integer.parseInt(userRegisterDTO.identityCard()),
                userRegisterDTO.email(),
                userRegisterDTO.email()
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
        return user.stream().map(MapperUtil::toDTO).toList();
    }

}

