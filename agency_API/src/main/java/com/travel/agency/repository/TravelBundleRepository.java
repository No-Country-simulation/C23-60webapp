package com.travel.agency.repository;

import com.travel.agency.model.entities.TravelBundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TravelBundleRepository extends JpaRepository<TravelBundle, Long> {

    List<TravelBundle> findByAvailableBundlesGreaterThan(Integer availableBundles);

    @Query("SELECT t FROM TravelBundle t WHERE LOWER(t.destiny) LIKE LOWER(CONCAT('%', :destiny, '%'))")
    List<TravelBundle> findByDestiny(@Param("destiny") String destiny);

}
