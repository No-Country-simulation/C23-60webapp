package com.travel.agency.repository;

import com.travel.agency.model.entities.DetailsPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface DetailsPurchaseRepository extends JpaRepository<DetailsPurchase, Long> {

    List<DetailsPurchase> findByPurchase_IdAndPurchase_User_Id(Long purchaseId, Long userId);

    Optional<DetailsPurchase> findByIdAndPurchase_User_Id(Long detailId, Long userId);
}
