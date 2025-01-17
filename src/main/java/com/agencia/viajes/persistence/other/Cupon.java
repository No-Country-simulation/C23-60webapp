package com.agencia.viajes.persistence.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Cupon {

    private String codigo;
    private Integer descuento;
}
