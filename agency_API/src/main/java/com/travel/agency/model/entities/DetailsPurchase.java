package com.travel.agency.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DetailsPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    //El precio unitario multiplicado por cantidad
    private Double totalPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_bundles_id", nullable = false)
    //Almacena el paquete de viaje unitario que el usuario quiere comprar.
    private TravelBundle travelBundle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    @JsonIgnore
    //Vincula detailsPurchase con la compra a la que pertenece. No hace falta mostrarla al usuario.
    private Purchase purchase;

    public DetailsPurchase(TravelBundle travelBundle, int quantity, Double totalPrice, Purchase purchase) {
        this.travelBundle = travelBundle;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.purchase = purchase;
    }
}
