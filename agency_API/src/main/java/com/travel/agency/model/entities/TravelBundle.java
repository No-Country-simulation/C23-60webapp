package com.travel.agency.model.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TravelBundle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String destiny;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer availableBundles;
    private Integer totalBundles;
    @OneToMany(mappedBy = "travelBundle",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<image> images;
    @OneToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;
    private Integer amountToBuy;
    private Double unitaryPrice;
    private Double totalPrice;
    @OneToMany(mappedBy = "travelBundle",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Rating> rating;


    //Calcular el precio del paquete seleccionado
    public Double getTotalPrice() {
        Double total = unitaryPrice * amountToBuy;
        //Si hay Cupon, aplicar
        if (coupon != null && coupon.getDiscount() != null) {
            total -= (total * coupon.getDiscount() / 100);
        }
        return total;
    }

    //Restar del inventario (cuando se suma al carrito)
    public void decreaseAvaliableBundles(Integer quantity) {
        if (availableBundles >= quantity) {
            availableBundles -= quantity;
        } else {
            throw new RuntimeException("Not enough avaliable boundles");
        }
    }

    //Aumentar inventario, devolver paquete a stock
    public void increaseAvailableBundles(Integer quantity) {
        availableBundles += quantity;
    }

    //Incrementar cantidad seleccionada
    public void increaseAmountToBuy(Integer quantity) {
        this.amountToBuy += quantity;
    }

    public void setAmountToBuy(int quantity) {
        this.amountToBuy = quantity;
    }

}
