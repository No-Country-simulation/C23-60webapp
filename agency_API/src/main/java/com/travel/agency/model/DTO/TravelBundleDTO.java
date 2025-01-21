package com.travel.agency.model.DTO;

import com.travel.agency.model.entities.TravelBundle;

public record TravelBundleDTO(
        Long id,
        String title,
        String destiny,
        Double unitaryPrice,
        Integer amountToBuy,
        String couponInfo // Cambiar a String para manejar mensajes personalizados

) {
    public TravelBundleDTO (TravelBundle travelBundle){
        this(
                travelBundle.getId(),
                travelBundle.getTitle(),
                travelBundle.getDestiny(),
                travelBundle.getUnitaryPrice(),
                travelBundle.getAmountToBuy(),
                //manejo de info de cupon, para mostar si tiene o no descuento
                travelBundle.getCoupon() != null
                        ? "Descuento: " + travelBundle.getCoupon().getDiscount() + "% - Código: " + travelBundle.getCoupon().getCode()
                        : "Sin descuento disponible" // Mensaje predeterminado
        );
    }
}
