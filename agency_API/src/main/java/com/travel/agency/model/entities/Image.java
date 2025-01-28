package com.travel.agency.model.entities;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Lob
    private byte[] image;
    //VER PARA EL MANEJO DE LLEVAR Y TRAER LAS IMGS DE BACK A FRONT Y VICEVERSA
    //private String contentType;  // tipo de la imagen (por ejemplo, "image/jpeg", "image/png")
    // private String filename;

    @ManyToOne
    @JoinColumn(name = "travel_bundle_id")
    private TravelBundle travelBundle;

}
