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
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //Precio total deducido de cada uno de los paquetes de viaje en detailsPurchase
    private Double totalPrice;
    private LocalDateTime purchaseDate;
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //Almacena la cantidad de paquetes de viaje que ha comprado el usuario.
    private List<DetailsPurchase> detailsPurchase;


}
