package com.bikerackapp.bike_rack_service.DTO;

import java.io.Serializable;

public record Location(
        double lat,
        double lng
) implements Serializable {}
