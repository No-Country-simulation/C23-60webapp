
package com.travel.agency.model.DTO.ShoppingCart;

public record DetailsShoppingCartDTO(
        Long id,
        String travelBundleName,
        Integer quantity,
        Double price) {

}
