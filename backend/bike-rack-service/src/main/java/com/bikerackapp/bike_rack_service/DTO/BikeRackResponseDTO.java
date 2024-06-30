package com.bikerackapp.bike_rack_service.DTO;

import java.io.Serializable;
import java.util.UUID;

public record BikeRackResponseDTO (
        Poi poi,
        String address,
        double rating,
        int theftsInLastMonth
) implements Serializable {}

