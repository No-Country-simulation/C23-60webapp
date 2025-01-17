package com.agencia.viajes.persistence.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Direccion {

    private String calle;
    private Integer numeroCalle;
    private String ciudad;
    private String estado;
    private String codigoZip;
    private String pais;



}
