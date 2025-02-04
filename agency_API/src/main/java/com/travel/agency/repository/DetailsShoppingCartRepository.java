package com.travel.agency.repository;

import com.travel.agency.model.entities.DetailsShoppingCart;
import com.travel.agency.model.entities.ShoppingCart;
import com.travel.agency.model.entities.TravelBundle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsShoppingCartRepository extends JpaRepository<DetailsShoppingCart, Long> {

    List<DetailsShoppingCart> findByShoppingCart(ShoppingCart shoppingCart);

    List<DetailsShoppingCart> findByTravelBundle(TravelBundle travelBundle);

}
