package com.travel.agency.repository;

import com.travel.agency.model.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository <Purchase,Long> {
}
