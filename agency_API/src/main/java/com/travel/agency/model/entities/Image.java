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
    private String filename;
    private String contentType;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imageData;
    //VER PARA EL MANEJO DE LLEVAR Y TRAER LAS IMGS DE BACK A FRONT Y VICEVERSA
     // tipo de la imagen (por ejemplo, "image/jpeg", "image/png")


    @ManyToOne
    @JoinColumn(name = "travel_bundle_id")
    private TravelBundle travelBundle;

    public Image(String filename, String contentType, String imageData, TravelBundle travelBundle) {
        this.filename = filename;
        this.contentType = contentType;
        this.imageData = imageData;
        this.travelBundle = travelBundle;
    }
}
