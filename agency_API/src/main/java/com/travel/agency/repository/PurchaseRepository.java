package com.travel.agency.repository;

import com.travel.agency.enums.Status;
import com.travel.agency.model.entities.Purchase;
import com.travel.agency.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository <Purchase,Long> {
   // Optional<Purchase> findByUserIdAndStatus(Long userId, Status status);

    List<Purchase> findByStatusAndPurchaseDateBefore(Status status, LocalDateTime timeoutLimit);

    List<Purchase> findByStatus(Status status);
}
