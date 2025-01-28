package com.travel.agency.mapper;

import com.travel.agency.model.DTO.TravelBundle.TravelBundleDTO;
import com.travel.agency.model.DTO.TravelBundle.TravelBundleRequestDTO;
import com.travel.agency.model.DTO.image.ImageDTO;
import com.travel.agency.model.DTO.rating.RatingDTO;
import com.travel.agency.model.DTO.user.MinUserDTO;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.model.entities.User;
import com.travel.agency.utils.MapperUtil;

import java.util.List;
import java.util.stream.Collectors;

public class TravelBundleMapper {

    public static TravelBundle toEntity(TravelBundleRequestDTO travelBundleRequestDTO, User user) {
        if (travelBundleRequestDTO == null) {
            return null;
        }
        TravelBundle travelBundle = new TravelBundle();
        travelBundle.setTitle(travelBundleRequestDTO.getTitle());
        travelBundle.setDescription(travelBundleRequestDTO.getDescription());
        travelBundle.setDestiny(travelBundleRequestDTO.getDestiny());
        travelBundle.setStartDate(travelBundleRequestDTO.getStartDate());
        travelBundle.setEndDate(travelBundleRequestDTO.getEndDate());
        travelBundle.setAvailableBundles(travelBundleRequestDTO.getAvailableBundles());
        travelBundle.setUnitaryPrice(travelBundleRequestDTO.getUnitaryPrice());
        travelBundle.setUserAdmin(user);

        return travelBundle;
    }

    public static TravelBundleDTO toDTO(TravelBundle travelBundle) {
        if (travelBundle == null) {
            return null;
        }
        return new TravelBundleDTO(
                travelBundle.getId(),
                travelBundle.getTitle(),
                travelBundle.getDescription(),
                travelBundle.getDestiny(),
                travelBundle.getStartDate(),
                travelBundle.getEndDate(),
                travelBundle.getAvailableBundles(),
                travelBundle.getUnitaryPrice(),
                //Mapea cada Rating entity y la convierte en una lista de RatingDTO
                travelBundle.getRating().stream().map(rating ->
                        MapperUtil.mapperEntity(rating1->new RatingDTO(
                                rating.getRating(), new MinUserDTO(
                                        rating.getUser().getFirstName(),
                                        rating.getUser().getLastName(),
                                        rating.getUser().getEmail()), rating.getComment()))).toList(),
                //Mapean cada imagen entity y la convierte en lista de ImageDTO
                travelBundle.getImages().stream().map(image -> MapperUtil
                        .mapperEntity(image1 -> new ImageDTO(image.getImage()))).toList()
        );
    }

    public static List<TravelBundleDTO> toDTOList(List<TravelBundle> travelBundles) {
        return travelBundles.stream().map(TravelBundleMapper::toDTO).collect(Collectors.toList());
    }
}
