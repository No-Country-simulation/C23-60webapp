package com.travel.agency.model.entities;

import com.travel.agency.model.DTO.rating.UpdateRating;
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

    public void updateRating(UpdateRating updateRating, TravelBundle travelBundle) {
        if (updateRating.rating() != null) {
            this.rating = updateRating.rating();
            this.creationDate = LocalDateTime.now();
        }
        if (updateRating.travelBundleId() != null && travelBundle != null && updateRating.travelBundleId().equals(travelBundle.getId())) {
            this.travelBundle = travelBundle;
        }
        if (updateRating.comment() != null) {
            this.comment = updateRating.comment();
            this.creationDate = LocalDateTime.now();
        }
    }

}
