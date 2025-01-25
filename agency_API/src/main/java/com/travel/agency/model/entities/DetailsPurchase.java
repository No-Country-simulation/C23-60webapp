package com.travel.agency.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DetailsPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private Double total_price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_bundles_id", nullable = false)
    @JsonIgnore
    private TravelBundle travelBundle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    @JsonIgnore
    private Purchase purchase;
    @OneToOne
    @JoinColumn(name = "coupon_id", nullable = true)
    private Coupon coupon;
}
