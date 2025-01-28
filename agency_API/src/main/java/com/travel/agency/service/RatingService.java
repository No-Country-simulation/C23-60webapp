package com.travel.agency.service;

import com.travel.agency.exceptions.ResourceNotFoundException;
import com.travel.agency.model.DTO.rating.CreateRatingDTO;
import com.travel.agency.model.DTO.rating.RatingDTO;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        //Buscar el usuario por username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        //Buscar el TravelBundle por su ID
        TravelBundle travelBundle = travelBundleRepository.findById(createRatingDTO.travelBundleId())
                .orElseThrow(() -> new EntityNotFoundException("Travel bundle not found with ID: " + createRatingDTO.travelBundleId()));
        //Verificar si el usuario ya califico el paquete
        boolean alreadyRated = ratingRepository.existsByUserAndTravelBundle(user, travelBundle);
        if (alreadyRated) {
            throw new IllegalStateException("User has already rated this travel");
        }
        // Crear la entidad Rating usando el MapperUtil
        Rating rating = new Rating(
                createRatingDTO.rating(),
                user,
                travelBundle,
                createRatingDTO.comment(),
                LocalDateTime.now()
        );
        //guardar
        Rating savedRating = ratingRepository.save(rating);
        return new RatingDTO(
                rating.getId(),
                rating.getUser().getUsername(),
                rating.getRating(),
                rating.getTravelBundle().getId(),
                rating.getComment(),
                rating.getCreationDate()
        );
    }

    public RatingDTO searchRatingByID(Long ratingId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating", "ID", ratingId));
        return new RatingDTO(
                rating.getId(),
                rating.getUser().getUsername(),
                rating.getRating(),
                rating.getTravelBundle().getId(),
                rating.getComment(),
                rating.getCreationDate()
        );
    }

    public List<RatingDTO> getAllRatings() {
        List<Rating> ratingList = ratingRepository.findAll();
        return ratingList.stream()
                .map(rating -> new RatingDTO(
                        rating.getId(),
                        rating.getUser().getUsername(),
                        rating.getRating(),
                        rating.getTravelBundle().getId(),
                        rating.getComment(),
                        rating.getCreationDate()
                ))
                .collect(Collectors.toList());
    }

    public List<RatingDTO> searchRatingsByUser(String username) {
        //Buscar el usuario por username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        List<Rating> ratingList = ratingRepository.findByUser(user);
        //pasar a dto
        return ratingList.stream()
                .map(rating -> new RatingDTO(
                        rating.getId(),
                        rating.getUser().getUsername(),
                        rating.getRating(),
                        rating.getTravelBundle().getId(),
                        rating.getComment(),
                        rating.getCreationDate()
                ))
                .collect(Collectors.toList());
    }
}
