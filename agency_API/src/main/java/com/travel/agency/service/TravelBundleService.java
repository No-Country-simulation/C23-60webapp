package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.mapper.TravelBundleMapper;
import com.travel.agency.model.DTO.TravelBundle.TravelBundleDTO;
import com.travel.agency.model.DTO.TravelBundle.TravelBundleRequestDTO;
import com.travel.agency.model.entities.Image;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.TravelBundleRepository;
import com.travel.agency.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TravelBundleService {

    private final TravelBundleRepository travelBundleRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Autowired
    public TravelBundleService(TravelBundleRepository travelBundleRepository, UserRepository userRepository, ImageService imageService) {
        this.travelBundleRepository = travelBundleRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    public TravelBundleDTO getTravelBundle(Long id) {
        TravelBundle travelBundle = this.findById(id);
        return TravelBundleMapper.toDTO(travelBundle);
    }

    public List<TravelBundleDTO> getListTravelBundleByAvailability() {
        List<TravelBundle> travelBundles = travelBundleRepository.findByAvailableBundlesGreaterThan(0);
        return TravelBundleMapper.toDTOList(travelBundles);
    }

    //Este metodo puede ser simplificado.
    @Transactional
    public void createTravelBundle(TravelBundleRequestDTO travelBundleRequestDTO,
                                   List<MultipartFile> images) {
        //Se obtiene el usuario admin logueado en ese momento.
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User adminUser = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        //Se crea provisoriamene la nueva entidad
        TravelBundle travelBundle = new TravelBundle(
                travelBundleRequestDTO.title(),
                travelBundleRequestDTO.description(),
                travelBundleRequestDTO.destiny(),
                travelBundleRequestDTO.startDate(),
                travelBundleRequestDTO.endDate(),
                travelBundleRequestDTO.availableBundles(),
                travelBundleRequestDTO.unitaryPrice(),
                travelBundleRequestDTO.discount()
        );
        //Mapea los datos de MultipartFile en objetos de tipo Image
        List<Image> imageList = images.stream().map(images1 -> {
            try {
                return this.imageService.createImage(
                        images1,
                        travelBundle
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList();
        travelBundle.setImages(imageList);
        travelBundle.setUserAdmin(adminUser);
        this.travelBundleRepository.save(travelBundle);
    }

    public TravelBundle findById(Long id) {
        return travelBundleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Travel Bundle", "Id", id));
    }

    public List<TravelBundleDTO> getAllTravelBundles() {
        List<TravelBundle> travelBundleList = this.travelBundleRepository.findAll();
        return travelBundleList.stream().map(TravelBundleMapper::toDTO).toList();
    }

}
