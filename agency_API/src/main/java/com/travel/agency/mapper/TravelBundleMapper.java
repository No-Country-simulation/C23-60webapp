package com.travel.agency.mapper;

import com.travel.agency.model.DTO.TravelBundle.TravelBundleDTO;
import com.travel.agency.model.DTO.TravelBundle.TravelBundleRequestDTO;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.model.entities.User;
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
        travelBundle.setUser(user);

        return travelBundle;
    }

    public static TravelBundleDTO toDTO(TravelBundle travelBundle) {
        if (travelBundle == null) {
            return null;
        }
        TravelBundleDTO travelBundleDTO = new TravelBundleDTO();
        travelBundleDTO.setId(travelBundle.getId());
        travelBundleDTO.setTitle(travelBundle.getTitle());
        travelBundleDTO.setDescription(travelBundle.getDescription());
        travelBundleDTO.setDestiny(travelBundle.getDestiny());
        travelBundleDTO.setStartDate(travelBundle.getStartDate());
        travelBundleDTO.setEndDate(travelBundle.getEndDate());
        travelBundleDTO.setAvailableBundles(travelBundle.getAvailableBundles());
        travelBundleDTO.setUnitaryPrice(travelBundle.getUnitaryPrice());
        return travelBundleDTO;
    }

    public static List<TravelBundleDTO> toDTOList(List<TravelBundle> travelBundles) {
        return travelBundles.stream().map(TravelBundleMapper::toDTO).collect(Collectors.toList());
    }
}
