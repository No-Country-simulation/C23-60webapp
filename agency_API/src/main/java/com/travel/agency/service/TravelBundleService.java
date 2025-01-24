package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.mapper.TravelBundleMapper;
import com.travel.agency.model.DTO.TravelBundle.*;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.TravelBundleRepository;
import com.travel.agency.repository.UserRepository;
import java.util.List;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TravelBundleService {

    private final TravelBundleRepository travelBundleRepository;
    private final UserRepository userRepository;

    public TravelBundleService(TravelBundleRepository travelBundleRepository, UserRepository userRepository) {
        this.travelBundleRepository = travelBundleRepository;
        this.userRepository = userRepository;
    }

    public TravelBundleDTO getTravelBundle(Long id) {
        TravelBundle travelBundle = this.findById(id);
        return TravelBundleMapper.toDTO(travelBundle);
    }

    public List<TravelBundleDTO> getListTravelBundle() {
        List<TravelBundle> travelBundles = travelBundleRepository.findByAvailableBundlesGreaterThan(0);
        return TravelBundleMapper.toDTOList(travelBundles);
    }

    public TravelBundleDTO createTravelBundle(TravelBundleRequestDTO travelBundleRequestDTO, String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));
        TravelBundle travelBundle = TravelBundleMapper.toEntity(travelBundleRequestDTO, user);
        travelBundleRepository.save(travelBundle);
        return TravelBundleMapper.toDTO(travelBundle);
    }

    public TravelBundle findById(Long id) {
        TravelBundle travelBundle = travelBundleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Travel Bundle", "Id", id));
        return travelBundle;
    }

}
