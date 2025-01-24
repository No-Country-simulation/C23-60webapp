package com.travel.agency.repository;

import com.travel.agency.model.entities.DetailsPurchase;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DetailsPurchaseRepository extends JpaRepository<DetailsPurchase, Long>  {
    
}
