package com.agencia.viajes.persistence.entities;


import com.agencia.viajes.persistence.other.Cupon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaqueteViaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    private String destino;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private Integer paquetesDisponibles;
    private Integer paquetesTotales;
    @OneToMany(mappedBy = "paqueteViaje",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Imagen> imagenes;
    @Embedded
    private Cupon cupon;
    private Integer Cantidad;
    private Double precioUnitario;
    private Double precioTotal;




}
