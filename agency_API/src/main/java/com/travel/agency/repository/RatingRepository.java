package com.travel.agency.repository;

import com.travel.agency.model.entities.Rating;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    boolean existsByUserAndTravelBundle(User user, TravelBundle travelBundle);

    List<Rating> findByUser(User user);
}
