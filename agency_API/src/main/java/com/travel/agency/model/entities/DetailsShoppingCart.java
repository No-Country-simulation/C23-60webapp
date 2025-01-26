package com.travel.agency.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DetailsShoppingCart {

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
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    @JsonIgnore
    //Vincula detailsPurchase con la compra a la que pertenece. No hace falta mostrarla al usuario.
    private ShoppingCart shoppingCart;

}
