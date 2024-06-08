package com.bikerackapp.bike_rack_service.controller;

import com.bikerackapp.bike_rack_service.DTO.CreateBikeRackRequestDTO;
import com.bikerackapp.bike_rack_service.DTO.UpdateBikeRackRequestDTO;
import com.bikerackapp.bike_rack_service.DTO.BikeRackResponseDTO;
import com.bikerackapp.bike_rack_service.service.BikeRackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BikeRackResponseDTO> createBikeRack(@RequestBody CreateBikeRackRequestDTO newBikeRack) {
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
        if (bikeRack != null) {
            return ResponseEntity.ok(bikeRack);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{rackId}")
    public ResponseEntity<BikeRackResponseDTO> updateBikeRack(@PathVariable("rackId") UUID rackId, @RequestBody UpdateBikeRackRequestDTO bikeRackToUpdate) {
        BikeRackResponseDTO updatedBikeRack = bikeRackService.updateBikeRack(rackId, bikeRackToUpdate);
        if (updatedBikeRack != null) {
            return ResponseEntity.ok(updatedBikeRack);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{rackId}/rating")
    public ResponseEntity<BikeRackResponseDTO> updateBikeRackRating(@PathVariable("rackId") UUID rackId, @RequestParam double newRating) {
        BikeRackResponseDTO updatedBikeRack = bikeRackService.updateRating(rackId, newRating);
        return new ResponseEntity<>(updatedBikeRack, HttpStatus.OK);
    }

    @DeleteMapping("/{rackId}")
    public ResponseEntity<Void> deleteReport(@PathVariable("rackId") UUID rackId) {
        boolean isDeleted = bikeRackService.deleteBikeRack(rackId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
