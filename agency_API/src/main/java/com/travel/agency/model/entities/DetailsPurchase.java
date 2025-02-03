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
    private String travelBundleTitle;
    private int quantity;
    private Double unitPrice;
    //El precio unitario multiplicado por cantidad
    private Double totalPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    @JsonIgnore
    //Vincula detailsPurchase con la compra a la que pertenece. No hace falta mostrarla al usuario.
    private Purchase purchase;

    public DetailsPurchase(String travelBundleTitle, int quantity, Double unitPrice, Double totalPrice, Purchase purchase) {
        this.travelBundleTitle = travelBundleTitle;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.purchase = purchase;
    }

}
