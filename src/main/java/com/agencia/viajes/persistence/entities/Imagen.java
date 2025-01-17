package com.agencia.viajes.persistence.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] imagen;
    @ManyToOne
    @JoinColumn(name = "paquete_viaje_id")
    private PaqueteViaje paqueteViaje;

}
