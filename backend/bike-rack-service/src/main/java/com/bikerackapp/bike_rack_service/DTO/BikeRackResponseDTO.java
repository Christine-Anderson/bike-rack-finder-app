package com.bikerackapp.bike_rack_service.DTO;

import java.io.Serializable;
import java.util.UUID;

public record BikeRackResponseDTO (
        UUID rackId,
        String address,
        double latitude,
        double longitude,
        double rating,
        int theftsInLastMonth
) implements Serializable {}
