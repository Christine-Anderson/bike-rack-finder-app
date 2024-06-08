package com.bikerackapp.bike_rack_service.service;

import com.bikerackapp.bike_rack_service.DTO.CreateBikeRackRequestDTO;
import com.bikerackapp.bike_rack_service.DTO.UpdateBikeRackRequestDTO;
import com.bikerackapp.bike_rack_service.DTO.BikeRackResponseDTO;
import com.bikerackapp.bike_rack_service.controller.BikeRackController;
import com.bikerackapp.bike_rack_service.exceoption.ResourceNotFoundException;
import com.bikerackapp.bike_rack_service.model.BikeRack;
import com.bikerackapp.bike_rack_service.repository.BikeRackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BikeRackService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BikeRackController.class);

    private final BikeRackRepository bikeRackRepository;

    @Autowired
    public BikeRackService(BikeRackRepository bikeRackRepository) {
        this.bikeRackRepository = bikeRackRepository;
    }

    public BikeRackResponseDTO createBikeRack(CreateBikeRackRequestDTO newBikeRack) {
        BikeRack bikeRack = new BikeRack(
                newBikeRack.latitude(),
                newBikeRack.longitude()
        );
        bikeRackRepository.save(bikeRack);
        LOGGER.info("Successfully created bikeRack with ID: {}", bikeRack.getRackId());
        return convertToDto(bikeRack);
    }

    public List<BikeRackResponseDTO> getAllBikeRacks() {
        List<BikeRack> bikeRacks = bikeRackRepository.findAll();
        return bikeRacks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BikeRackResponseDTO getBikeRackById(UUID bikeRackId) {
        BikeRack bikeRack = bikeRackRepository.findById(bikeRackId)
                .orElseThrow(() -> new ResourceNotFoundException("Report with ID " + bikeRackId + " not found"));
        return convertToDto(bikeRack);
    }

    public BikeRackResponseDTO updateBikeRack(UUID bikeRackId, UpdateBikeRackRequestDTO bikeRackRequestDTO) {
        BikeRack bikeRack = bikeRackRepository.findById(bikeRackId)
                .orElseThrow(() -> new ResourceNotFoundException("Report with ID " + bikeRackId + " not found"));
        bikeRack.setLatitude(bikeRackRequestDTO.latitude());
        bikeRack.setLongitude(bikeRackRequestDTO.longitude());
        bikeRack.setRating(bikeRackRequestDTO.rating());
        bikeRack.setTheftsInLastMonth(bikeRackRequestDTO.theftsInLastMonth());
        bikeRackRepository.save(bikeRack);
        LOGGER.info("Successfully updated bikeRack with ID: {}", bikeRackId);
        return convertToDto(bikeRack);
    }

    public BikeRackResponseDTO updateRating(UUID bikeRackId, double newRating) {
        BikeRack bikeRack = bikeRackRepository.findById(bikeRackId)
                .orElseThrow(() -> new ResourceNotFoundException("Report with ID " + bikeRackId + " not found"));
        int numRatings = bikeRack.getNumRatings();
        double totalRating = (bikeRack.getRating() * (double) numRatings);
        bikeRack.setRating((totalRating + newRating) / (double) (numRatings + 1));
        bikeRack.incrementNumRatings();
        bikeRackRepository.save(bikeRack);
        return convertToDto(bikeRack);
    }

    public boolean deleteBikeRack(UUID bikeRackId) {
        BikeRack bikeRack = bikeRackRepository.findById(bikeRackId)
                .orElseThrow(() -> new ResourceNotFoundException("Report with ID " + bikeRackId + " not found"));
        bikeRackRepository.deleteById(bikeRackId);
        LOGGER.info("Successfully deleted bikeRack with ID: {}", bikeRackId);
        return true;
    }

    private BikeRackResponseDTO convertToDto(BikeRack bikeRack) {
        return new BikeRackResponseDTO(
                bikeRack.getRackId(),
                bikeRack.getLatitude(),
                bikeRack.getLongitude(),
                bikeRack.getRating(),
                bikeRack.getTheftsInLastMonth()
        );
    }
}
