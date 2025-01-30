package com.travel.agency.mapper;

import com.travel.agency.model.DTO.DetailsPurchase.DetailsPurchaseDTO;
import com.travel.agency.model.entities.DetailsPurchase;
import com.travel.agency.model.entities.DetailsShoppingCart;
import com.travel.agency.model.entities.Purchase;
import com.travel.agency.model.entities.ShoppingCart;

import java.util.List;
import java.util.stream.Collectors;


public class DetailsPurchaseMapper {


    public static DetailsPurchaseDTO toDTO(DetailsPurchase detailsPurchase) {
        if (detailsPurchase == null) {
            return null;
        }
        DetailsPurchaseDTO dto = new DetailsPurchaseDTO(
                detailsPurchase.getId(),
                detailsPurchase.getTravelBundle().getTitle(),
                detailsPurchase.getQuantity(),
                detailsPurchase.getTotalPrice()
        );
        return dto;
    }

    // Conversión de lista de entidades a lista de DTOs
    public static List<DetailsPurchaseDTO> toDTOList(List<DetailsPurchase> detailsPurchases) {
        return detailsPurchases.stream()
                .map(DetailsPurchaseMapper::toDTO)
                .collect(Collectors.toList());
    }

//    //convertir de detailsShoppingCart a detailsPurchase
//    public static DetailsPurchaseDTO converterFromDetailsCart(DetailsShoppingCart detailsShoppingCart) {
//        return new DetailsPurchaseDTO(
//                detailsShoppingCart.getId(),
//                detailsShoppingCart.getTravelBundle().getTitle(),
//                detailsShoppingCart.getQuantity(),
//                detailsShoppingCart.getTotalPrice() * detailsShoppingCart.getQuantity()
//        );
//    }
    public static List<DetailsPurchase> convertCartDetailsToPurchaseDetails(ShoppingCart shoppingCart, Purchase purchase) {
        return shoppingCart.getDetailsShoppingCarts().stream()
                .map(cartDetail -> new DetailsPurchase(
                        cartDetail.getTravelBundle(),
                        cartDetail.getQuantity(),
                        cartDetail.getTotalPrice(),
                        purchase
                )).collect(Collectors.toList());
    }


}
