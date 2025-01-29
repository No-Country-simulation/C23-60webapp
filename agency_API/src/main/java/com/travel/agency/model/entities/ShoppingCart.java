package com.travel.agency.model.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    //Precio total deducido de cada uno de los paquetes de viaje en detailsPurchase
    private Double totalPrice = 0.0;
    private LocalDateTime purchaseDate;
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //Almacena la cantidad de paquetes de viaje que ha comprado el usuario.
    private List<DetailsShoppingCart> detailsShoppingCarts = new ArrayList<>();

}
