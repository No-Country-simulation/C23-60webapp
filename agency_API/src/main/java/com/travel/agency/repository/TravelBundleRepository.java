package com.travel.agency.repository;

import com.travel.agency.model.entities.TravelBundle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelBundleRepository extends JpaRepository<TravelBundle, Long> {

    List<TravelBundle> findByAvailableBundlesGreaterThan(Integer availableBundles);

}
