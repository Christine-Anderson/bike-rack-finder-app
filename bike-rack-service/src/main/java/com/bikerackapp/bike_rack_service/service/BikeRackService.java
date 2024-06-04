package com.bikerackapp.bike_rack_service.service;

import com.bikerackapp.bike_rack_service.DTO.BikeRackRequestDTO;
import com.bikerackapp.bike_rack_service.DTO.BikeRackResponseDTO;
import com.bikerackapp.bike_rack_service.controller.BikeRackController;
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
    private static final String REPORT_NOT_FOUND_WARNING = "Bike rack with ID: {} not found";
    private static final Logger LOGGER = LoggerFactory.getLogger(BikeRackController.class);

    private final BikeRackRepository bikeRackRepository;

    @Autowired
    public BikeRackService(BikeRackRepository bikeRackRepository) {
        this.bikeRackRepository = bikeRackRepository;
    }

    public BikeRackResponseDTO createBikeRack(BikeRackRequestDTO newBikeRack) {
        BikeRack bikeRack = new BikeRack(
                newBikeRack.latitude(),
                newBikeRack.longitude(),
                newBikeRack.rating()
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
        BikeRack bikeRack = bikeRackRepository.findById(bikeRackId).orElse(null);
        if (bikeRack != null) {
            return convertToDto(bikeRack);
        } else {
            return null;
        }
    }

    public BikeRackResponseDTO updateBikeRack(UUID bikeRackId, BikeRackRequestDTO bikeRackRequestDTO) {
        BikeRack bikeRack = bikeRackRepository.findById(bikeRackId).orElse(null);
        if (bikeRack != null) {
            bikeRack.setLatitude(bikeRackRequestDTO.latitude());
            bikeRack.setLongitude(bikeRackRequestDTO.longitude());
            bikeRack.setRating(bikeRackRequestDTO.rating());
            bikeRackRepository.save(bikeRack);
            LOGGER.info("Successfully updated bikeRack with ID: {}", bikeRackId);
            return convertToDto(bikeRack);
        } else {
            LOGGER.warn(REPORT_NOT_FOUND_WARNING, bikeRackId);
            return null;
        }
    }

    public boolean deleteBikeRack(UUID bikeRackId) {
        if (bikeRackRepository.existsById(bikeRackId)) {
            bikeRackRepository.deleteById(bikeRackId);
            LOGGER.info("Successfully deleted bikeRack with ID: {}", bikeRackId);
            return true;
        } else {
            LOGGER.warn(REPORT_NOT_FOUND_WARNING, bikeRackId);
            return false;
        }
    }

    private BikeRackResponseDTO convertToDto(BikeRack bikeRack) {
        return new BikeRackResponseDTO(
                bikeRack.getRackId(),
                bikeRack.getLatitude(),
                bikeRack.getLongitude(),
                bikeRack.getRating()
        );
    }
}
