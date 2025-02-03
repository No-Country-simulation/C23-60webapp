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

    public static TravelBundle toEntity(TravelBundleRequestDTO dto) {
        return new TravelBundle(
                dto.title(),
                dto.description(),
                dto.destiny(),
                dto.startDate(),
                dto.endDate(),
                dto.availableBundles(),
                dto.unitaryPrice(),
                dto.discount()
        );
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
                travelBundle.getRating().stream().map(rating
                        -> MapperUtil.mapperEntity(rating1 -> new RatingDTO(
                rating.getId(),
                rating.getUser().getUsername(),
                rating.getRating(),
                rating.getTravelBundle().getId(),
                rating.getComment(),
                rating.getCreationDate()))).toList(),
                //Mapean cada imagen entity y la convierte en lista de ImageDTO
                travelBundle.getImages().stream().map(image -> MapperUtil
                .mapperEntity(image1 -> new ImageDTO(
                image.getFilename(),
                image.getContentType(),
                image.getImageData()))).toList(),
                travelBundle.getDiscount()
        );
    }

    public static List<TravelBundleDTO> toDTOList(List<TravelBundle> travelBundles) {
        return travelBundles.stream().map(TravelBundleMapper::toDTO).collect(Collectors.toList());
    }

    public static void updateFromDTO(TravelBundleRequestDTO travelBundleRequestDTO, TravelBundle travelBundle) {

        if (travelBundleRequestDTO.title() != null) {
            travelBundle.setTitle(travelBundleRequestDTO.title());
        }

        if (travelBundleRequestDTO.description() != null) {
            travelBundle.setDescription(travelBundleRequestDTO.description());
        }

        if (travelBundleRequestDTO.destiny() != null) {
            travelBundle.setDestiny(travelBundleRequestDTO.destiny());
        }

        if (travelBundleRequestDTO.startDate() != travelBundleRequestDTO.startDate()) {
            travelBundle.setStartDate(travelBundleRequestDTO.startDate());
        }

        if (travelBundleRequestDTO.endDate() != travelBundleRequestDTO.endDate()) {
            travelBundle.setEndDate(travelBundleRequestDTO.endDate());
        }

        if (travelBundleRequestDTO.availableBundles() != null) {
            travelBundle.setAvailableBundles(travelBundleRequestDTO.availableBundles());
        }

        if (travelBundleRequestDTO.unitaryPrice() != null) {
            travelBundle.setUnitaryPrice(travelBundleRequestDTO.unitaryPrice());
        }

    }
}
