package com.travel.agency.persistence.entities;


import com.travel.agency.persistence.other.Voucher;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private Integer totalBundles;
    @OneToMany(mappedBy = "travelBundle",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<image> images;
    @Embedded
    private Voucher voucher;
    private Integer amountToBuy;
    private Double unitaryPrice;
    private Double totalPrice;
    @OneToMany(mappedBy = "travelBundle",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Rating> rating;




}
