package com.travel.agency.repository;

import com.travel.agency.enums.Status;
import com.travel.agency.model.entities.Purchase;
import com.travel.agency.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PurchaseRepository extends JpaRepository <Purchase,Long> {
    Optional<Purchase> findByUserIdAndStatus(Long userId, Status status);

//    //eliminar la relación entre la compra y el paquete.
//    @Modifying
//    @Query("DELETE FROM PurchaseTravelBundle ptb WHERE ptb.purchase.id = :purchaseId AND ptb.travelBundle.id = :travelBundleId")
//    void deleteByPurchaseIdAndTravelBundleId(@Param("purchaseId") Long purchaseId, @Param("travelBundleId") Long travelBundleId);
}
