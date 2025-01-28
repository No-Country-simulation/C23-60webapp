package com.travel.agency.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "travel_bundle_id")
    private TravelBundle travelBundle;
    private String comment;
    private LocalDateTime creationDate;

    public Rating(Double rating, User user, TravelBundle travelBundle, String comment, LocalDateTime creationDate) {
        this.rating = rating;
        this.user = user;
        this.travelBundle = travelBundle;
        this.comment = comment;
        this.creationDate = creationDate;
    }


}
