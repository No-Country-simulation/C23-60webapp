package com.travel.agency.persistence.entities;

import com.travel.agency.enums.Status;
import com.travel.agency.persistence.other.Voucher;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<TravelBundle> travelBundles;
    @Embedded
    private Voucher voucher;
    private Double totalPrice;
    private LocalDateTime purchaseDate;
    private String paymentMethod;
    private Status status;
}
