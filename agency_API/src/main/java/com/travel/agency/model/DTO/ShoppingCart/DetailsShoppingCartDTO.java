
package com.travel.agency.model.DTO.ShoppingCart;

public record DetailsShoppingCartDTO(
        Long id,
        Long travelBundleId,
        String travelBundleName,
        Integer quantity,
        Double price) {

}
