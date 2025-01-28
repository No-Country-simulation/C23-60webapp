package com.travel.agency.model.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private Double unitaryPrice;
    @OneToMany(mappedBy = "travelBundle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Rating> rating = new ArrayList<>();
    @OneToMany(mappedBy = "travelBundle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    //Este campo almacena el usuario que creo el paquete viaje
    private User userAdmin;
    @Embedded
    private Discount discount;

    public TravelBundle(String title, String description, String destiny, LocalDate startDate, LocalDate endDate, Integer availableBundles, Double unitaryPrice, Discount discount) {
        this.title = title;
        this.description = description;
        this.destiny = destiny;
        this.startDate = startDate;
        this.endDate = endDate;
        this.availableBundles = availableBundles;
        this.unitaryPrice = unitaryPrice;
        this.discount = discount;
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


}
