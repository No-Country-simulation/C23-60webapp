package com.travel.agency.mapper;

import com.travel.agency.model.DTO.purchase.PurchaseDTO;
import com.travel.agency.model.entities.Purchase;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseMapper {
    public static List<PurchaseDTO> purchaseMapperDto(List<Purchase> purchaseList) {
        return purchaseList.stream()
                .map(PurchaseDTO::new)
                .collect(Collectors.toList());
    }

}
