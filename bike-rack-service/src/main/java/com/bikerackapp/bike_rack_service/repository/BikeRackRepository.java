package com.bikerackapp.bike_rack_service.repository;

import com.bikerackapp.bike_rack_service.model.BikeRack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface BikeRackRepository extends JpaRepository<BikeRack, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE BikeRack b SET b.theftsInLastMonth = ?2 WHERE b.rackId = ?1")
    void updateTheftsInLastMonth(UUID bikeRackId, int theftsInLastMonth);
}
