package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.mapper.RatingMapper;
import com.travel.agency.model.DTO.rating.CreateRatingDTO;
import com.travel.agency.model.DTO.rating.RatingDTO;
import com.travel.agency.model.DTO.rating.UpdateRating;
import com.travel.agency.model.entities.Rating;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.model.entities.User;
import com.travel.agency.repository.RatingRepository;
import com.travel.agency.repository.TravelBundleRepository;
import com.travel.agency.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final TravelBundleRepository travelBundleRepository;

    public RatingService(RatingRepository repository, UserRepository userRepository, TravelBundleRepository travelBundleRepository) {
        this.ratingRepository = repository;
        this.userRepository = userRepository;
        this.travelBundleRepository = travelBundleRepository;
    }


    @Transactional
    public RatingDTO createRating(CreateRatingDTO createRatingDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        TravelBundle travelBundle = travelBundleRepository.findById(createRatingDTO.travelBundleId())
                .orElseThrow(() -> new EntityNotFoundException("Travel bundle not found with ID: " + createRatingDTO.travelBundleId()));
        boolean alreadyRated = ratingRepository.existsByUserAndTravelBundle(user, travelBundle);
        if (alreadyRated) {
            throw new IllegalStateException("User has already rated this travel");
        }
        Rating rating = RatingMapper.toRating(createRatingDTO, user, travelBundle);
        Rating savedRating = ratingRepository.save(rating);
        return RatingMapper.toRatingDTO(savedRating);
    }

    public RatingDTO searchRatingByID(Long ratingId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating", "ID", ratingId));
        return RatingMapper.toRatingDTO(rating);
    }

    public List<RatingDTO> getAllRatings() {
        List<Rating> ratingList = ratingRepository.findAll();
        return RatingMapper.toRatingDtoList(ratingList);
    }

    public List<RatingDTO> searchRatingsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        List<Rating> ratingList = ratingRepository.findByUser(user);
        return RatingMapper.toRatingDtoList(ratingList);
    }

    @Transactional
    public void deleteRatingByIc(Long ratingId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating", "ID", ratingId));
        ratingRepository.deleteById(ratingId);
    }

    public List<RatingDTO> getRatingByTravelBundle(Long travelBundleId) {
        TravelBundle travelBundle = travelBundleRepository.findById(travelBundleId)
                .orElseThrow(() -> new EntityNotFoundException("Travel bundle not found with ID: " + travelBundleId));
        List<Rating> ratingList = travelBundle.getRating();
        return RatingMapper.toRatingDtoList(ratingList);
    }

    @Transactional
    public RatingDTO updateRating(UpdateRating updateRating, String username) {
        Rating rating = ratingRepository.getReferenceById(updateRating.id());
        TravelBundle travelBundle = travelBundleRepository.findById(updateRating.travelBundleId())
                .orElse(null);
        rating.updateRating(updateRating, travelBundle);
        Rating updatedRating = ratingRepository.save(rating);
        return RatingMapper.toRatingDTO(updatedRating);
    }
}
