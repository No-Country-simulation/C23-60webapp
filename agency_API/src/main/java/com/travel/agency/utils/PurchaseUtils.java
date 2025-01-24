package com.travel.agency.utils;

import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.entities.Purchase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PurchaseUtils {

    public List<PurchaseDTO> purchaseMapperDto(List<Purchase> purchaseList) {
        if (purchaseList == null || purchaseList.isEmpty()) {
            //VER RETORNO
            return null;
        }
        return purchaseList.stream()
                .map(PurchaseDTO::new)
                .collect(Collectors.toList());
    }

    public PurchaseDTO convertToPurchaseDto(Purchase purchase) {
        return new PurchaseDTO(purchase);
    }
}
