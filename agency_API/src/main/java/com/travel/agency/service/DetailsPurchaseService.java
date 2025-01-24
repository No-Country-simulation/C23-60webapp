package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.mapper.DetailsPurchaseMapper;
import com.travel.agency.mapper.TravelBundleMapper;
import com.travel.agency.model.DTO.DetailsPurchase.*;
import com.travel.agency.model.entities.DetailsPurchase;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.DetailsPurchaseRepository;
import com.travel.agency.repository.TravelBundleRepository;
import com.travel.agency.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetailsPurchaseService {

    private final TravelBundleService travelBundleService;
    private final DetailsPurchaseRepository detailsPurchaseRepository;
    private final TravelBundleRepository travelBundleRepository;
    private final UserRepository userRepository;

    public DetailsPurchaseService(TravelBundleService travelBundleService, DetailsPurchaseRepository detailsPurchaseRepository, TravelBundleRepository travelBundleRepository, UserRepository userRepository) {
        this.travelBundleService = travelBundleService;
        this.detailsPurchaseRepository = detailsPurchaseRepository;
        this.travelBundleRepository = travelBundleRepository;
        this.userRepository = userRepository;
    }

    public DetailsPurchaseDTO addTravelBundle(DetailsPurchaseRequestDTO detailsPurchaseRequestDTO,String userName) {        
        User user = userRepository.findByUsername(userName)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));

        
        TravelBundle travelBundle = travelBundleService.findById(detailsPurchaseRequestDTO.getTravelBundleId());
        
        DetailsPurchase detailsPurchase = DetailsPurchaseMapper.toEntity(detailsPurchaseRequestDTO,travelBundle);
        detailsPurchaseRepository.save(detailsPurchase);
        return DetailsPurchaseMapper.toDTO(detailsPurchase);
    }

}
