package com.travel.agency.repository;

import com.travel.agency.model.entities.DetailsShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DetailsShoppingCartRepository extends JpaRepository<DetailsShoppingCart, Long>   {
    
}
