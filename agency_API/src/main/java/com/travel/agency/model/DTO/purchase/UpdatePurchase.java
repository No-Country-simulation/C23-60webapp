package com.travel.agency.model.DTO.purchase;

public record UpdatePurchase(
        Long travelBundleId,
        Integer newAmountToBuy
) {

}
