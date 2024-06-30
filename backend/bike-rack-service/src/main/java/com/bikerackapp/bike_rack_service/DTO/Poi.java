package com.bikerackapp.bike_rack_service.DTO;

import java.io.Serializable;
import java.util.UUID;

public record Poi(
        UUID rackId,
        Location location
) implements Serializable {}
