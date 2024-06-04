package com.bikerackapp.bike_rack_service.repository;

import com.bikerackapp.bike_rack_service.model.BikeRack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BikeRackRepository extends JpaRepository<BikeRack, UUID> {
}
