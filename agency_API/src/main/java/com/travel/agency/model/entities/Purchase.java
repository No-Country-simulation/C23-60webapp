package com.travel.agency.model.entities;


import com.travel.agency.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<TravelBundle> travelBundles;
    private Double totalPrice;
    private LocalDateTime purchaseDate;
    private String paymentMethod;
    private Status status;


    //Inicializo la lista de paquetes y el precio en 0, para evitar nullPoniterException
    public Purchase() {
        this.travelBundles = new ArrayList<>();
        this.totalPrice = 0.0;
    }
    //Método para agregar al carrito
    public void addTravelBundle(TravelBundle travelBundle, Integer quantity) {
        // Verificar si el travelBundle es nulo
        if (travelBundle == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Travel bundle cannot be null");
        }

        // Verificar disponibilidad del paquete
        if (travelBundle.getAvailableBundles() < quantity) {
            throw new RuntimeException("No available bundles for this package");
        }

        //Primero ver si el paquete ya esta en el carrito
        for (TravelBundle bundle : travelBundles) {
            if (bundle.getId().equals(travelBundle.getId())) {
                //Si ya esta, incremento la cantidad
                bundle.increaseAmountToBuy(quantity);
                updateTotalPrice();
                return;
            }
        }
        //Restar en total de paquetes disponibles
        travelBundle.decreaseAvaliableBundles(quantity);
        //Setear cantidad seleccionada en el paquete
        travelBundle.setAmountToBuy(quantity);
        // Agregar el paquete a la lista de travelBundles
        travelBundles.add(travelBundle);
        //Acutalizar precio
        updateTotalPrice();
    }

    //Eliminar un paquete del  carrito
    public void removeTravelBundle(Long travelBundleId) {
        //Remover si cumple la condicion
        travelBundles.removeIf(bundle -> {
            boolean match = bundle.getId().equals(travelBundleId);
            //si coincide devuelvo la cantidad paquetes disponibles
            if (match) {
                //Devolver la cantidad al inventario
                bundle.increaseAvailableBundles(bundle.getAmountToBuy());
            }
            return match;
        });
        updateTotalPrice();
    }

    // Calcular y actualizar el precio total
    public void updateTotalPrice() {
        this.totalPrice = travelBundles.stream()
                .mapToDouble(TravelBundle::getTotalPrice)
                .sum();
    }
}
