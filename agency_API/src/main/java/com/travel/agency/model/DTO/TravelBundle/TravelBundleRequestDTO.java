package com.travel.agency.model.DTO.TravelBundle;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TravelBundleRequestDTO {

    @NotBlank(message = "Title cannot be empty.")
    @Size(max = 50, message = "Title cannot be longer than 50 characters.")
    private String title;

    @NotBlank(message = "Description cannot be empty.")
    @Size(max = 500, message = "Description cannot be longer than 500 characters.")
    private String description;

    @NotBlank(message = "Destination cannot be empty.")
    @Size(max = 100, message = "Destination cannot be longer than 100 characters.")
    private String destiny;

    @NotNull(message = "Start date cannot be null.")
    @FutureOrPresent(message = "Start date must be today or in the future.")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null.")
    @Future(message = "End date must be a future date.")
    private LocalDate endDate;

    @NotNull(message = "Number of available bundles cannot be null.")
    @PositiveOrZero(message = "Number of available bundles cannot be negative.")
    private Integer availableBundles;

    @NotNull(message = "Unit price cannot be null.")
    @Positive(message = "Unit price must be greater than zero.")
    private Double unitaryPrice;

    @AssertTrue(message = "The end date must be after the start date.")
    public boolean isEndDateAfterStartDate() {
        if (startDate != null && endDate != null) {
            return endDate.isAfter(startDate);
        }
        return true;
    }
}
