package com.bikerackapp.bike_rack_service.DTO;

public record BikeRackRequestDTO(
        double latitude,
        double longitude,
        double rating
) {}
