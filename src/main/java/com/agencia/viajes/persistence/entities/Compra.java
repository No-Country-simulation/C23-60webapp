package com.agencia.viajes.persistence.entities;

import com.agencia.viajes.enums.Status;
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
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<PaqueteViaje> paqueteViajes;
    @Embedded
    private Cupon cupon;
    private Double precioTotal;
    private LocalDate fechaCompra;
    private String metodoPago;
    private Status status;
}
