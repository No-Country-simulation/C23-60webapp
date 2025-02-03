package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.exceptions.UnauthorizedAccessException;
import com.travel.agency.mapper.TravelBundleMapper;
import com.travel.agency.model.DTO.TravelBundle.TravelBundleDTO;
import com.travel.agency.model.DTO.TravelBundle.TravelBundleRequestDTO;
import com.travel.agency.model.entities.Image;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.TravelBundleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import org.springframework.context.annotation.Lazy;

@Service
public class TravelBundleService {

    private final TravelBundleRepository travelBundleRepository;
    private final AuthService authService;
    private final ImageService imageService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public TravelBundleService(TravelBundleRepository travelBundleRepository, AuthService authService, ImageService imageService, @Lazy ShoppingCartService shoppingCartService) {
        this.travelBundleRepository = travelBundleRepository;
        this.authService = authService;
        this.imageService = imageService;
        this.shoppingCartService = shoppingCartService;
    }

    public TravelBundle findById(Long id) {
        return travelBundleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Travel Bundle", "Id", id));
    }

    public List<TravelBundleDTO> getAllTravelBundles() {
        List<TravelBundle> travelBundleList = this.travelBundleRepository.findAll();
        return travelBundleList.stream().map(TravelBundleMapper::toDTO).toList();
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
        User user = authService.getUser();
        TravelBundle travelBundle = TravelBundleMapper.toEntity(travelBundleRequestDTO);
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
        travelBundle.setUserAdmin(user);
        this.travelBundleRepository.save(travelBundle);
    }

    @Transactional
    public void updateTravelBundle(Long id, TravelBundleRequestDTO travelBundleRequestDTO) {
        User user = authService.getUser();
        TravelBundle travelBundle = this.findById(id);
        double price = travelBundle.getUnitaryPrice();
        verify(travelBundle, user);
        TravelBundleMapper.updateFromDTO(travelBundleRequestDTO, travelBundle);
        travelBundleRepository.save(travelBundle);
        if (price != travelBundle.getUnitaryPrice()) {
            shoppingCartService.updateTravelUpdateShoppingCart(travelBundle);
        }
    }

    @Transactional
    public void deleteTravelBundle(Long id) {
        User user = authService.getUser();
        TravelBundle travelBundle = this.findById(id);
        verify(travelBundle, user);
        shoppingCartService.deleteTravelUpdateShoppingCart(travelBundle);
        travelBundleRepository.delete(travelBundle);
    }

    public void verify(TravelBundle travelBundle, User user) {
        if (!travelBundle.getUserAdmin().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("This Travel Bundle does not belong to you.");
        }
    }

}
