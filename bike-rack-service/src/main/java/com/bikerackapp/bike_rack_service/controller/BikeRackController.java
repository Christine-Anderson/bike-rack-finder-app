package com.bikerackapp.bike_rack_service.controller;

import com.bikerackapp.bike_rack_service.DTO.CreateBikeRackRequestDTO;
import com.bikerackapp.bike_rack_service.DTO.UpdateBikeRackRequestDTO;
import com.bikerackapp.bike_rack_service.DTO.BikeRackResponseDTO;
import com.bikerackapp.bike_rack_service.service.BikeRackService;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bikeRack")
public class BikeRackController {

    private final BikeRackService bikeRackService;

    @Autowired
    public BikeRackController(BikeRackService bikeRackService) {
        this.bikeRackService = bikeRackService;
    }

    @PostMapping
    public ResponseEntity<BikeRackResponseDTO> createBikeRack(@Validated @RequestBody CreateBikeRackRequestDTO newBikeRack) {
        BikeRackResponseDTO createdBikeRack = bikeRackService.createBikeRack(newBikeRack);
        return new ResponseEntity<>(createdBikeRack, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BikeRackResponseDTO>> getBikeRacks() {
        List<BikeRackResponseDTO> bikeRacks = bikeRackService.getAllBikeRacks();
        return ResponseEntity.ok(bikeRacks);
    }

    @GetMapping("/{rackId}")
    public ResponseEntity<BikeRackResponseDTO> getBikeRackById(@PathVariable("rackId") UUID rackId) {
        BikeRackResponseDTO bikeRack = bikeRackService.getBikeRackById(rackId);
        return ResponseEntity.ok(bikeRack);
    }

    @PutMapping("/{rackId}")
    public ResponseEntity<BikeRackResponseDTO> updateBikeRack(
            @PathVariable("rackId") UUID rackId,
            @Validated @RequestBody UpdateBikeRackRequestDTO bikeRackToUpdate
    ) {
        BikeRackResponseDTO updatedBikeRack = bikeRackService.updateBikeRack(rackId, bikeRackToUpdate);
        return ResponseEntity.ok(updatedBikeRack);
    }

    @PutMapping("/{rackId}/rating")
    public ResponseEntity<BikeRackResponseDTO> updateBikeRackRating(
            @PathVariable("rackId") UUID rackId,
            @RequestParam @DecimalMin("0.0") @DecimalMax("5.0") double newRating
    ) {
        BikeRackResponseDTO updatedBikeRack = bikeRackService.updateRating(rackId, newRating);
        return new ResponseEntity<>(updatedBikeRack, HttpStatus.OK);
    }

    @DeleteMapping("/{rackId}")
    public ResponseEntity<Void> deleteReport(@PathVariable("rackId") UUID rackId) {
        bikeRackService.deleteBikeRack(rackId);
        return ResponseEntity.noContent().build();
    }
}
