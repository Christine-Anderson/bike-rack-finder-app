package com.bikerackapp.bike_rack_service.DTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

public record UpdateBikeRackRequestDTO(
        @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
        @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
        double latitude,

        @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
        @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
        double longitude,

        @DecimalMin(value = "0.0", message = "Rating must be between 0 and 5")
        @DecimalMax(value = "5.0", message = "Rating must be between 0 and 5")
        double rating,

        @Min(value = 0, message = "Thefts in last month must be non-negative")
        int theftsInLastMonth
) {}
