package com.travel.agency.model.DTO.DetailsPurchase;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailsPurchaseRequestDTO {

    @NotNull(message = "La cantidad es obligatoria.")
    @Min(value = 1, message = "La cantidad debe ser al menos 1.")
    private Integer quantity;

    @NotNull(message = "El precio total es obligatorio.")
    @Positive(message = "El precio total debe ser un valor positivo.")
    private Double totalPrice;

    @NotNull(message = "El ID del paquete de viaje es obligatorio.")
    private Long travelBundleId;

    private Long couponId;
}
