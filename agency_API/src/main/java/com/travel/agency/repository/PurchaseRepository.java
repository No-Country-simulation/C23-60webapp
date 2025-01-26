package com.travel.agency.repository;

import com.travel.agency.model.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository <Purchase,Long> {
   // Optional<Purchase> findByUserIdAndStatus(Long userId, Status status);

    //List<Purchase> findByStatusAndPurchaseDateBefore(Status status, LocalDateTime timeoutLimit);

    //List<Purchase> findByStatus(Status status);
}
