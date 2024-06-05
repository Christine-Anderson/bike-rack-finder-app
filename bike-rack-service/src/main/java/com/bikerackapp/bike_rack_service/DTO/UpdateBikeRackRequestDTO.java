package com.bikerackapp.bike_rack_service.DTO;

public record UpdateBikeRackRequestDTO(
        double latitude,
        double longitude,
        double rating,
        int theftsInLastMonth
) {}
