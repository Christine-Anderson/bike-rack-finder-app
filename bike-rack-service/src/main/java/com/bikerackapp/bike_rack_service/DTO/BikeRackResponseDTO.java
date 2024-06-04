package com.bikerackapp.bike_rack_service.DTO;

import java.util.UUID;

public record BikeRackResponseDTO(
        UUID rackId,
        double latitude,
        double longitude,
        double rating
) {}