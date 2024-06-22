package com.bikerackapp.bike_rack_service.service;

import com.bikerackapp.bike_rack_service.DTO.CreateBikeRackRequestDTO;
import com.bikerackapp.bike_rack_service.DTO.UpdateBikeRackRequestDTO;
import com.bikerackapp.bike_rack_service.DTO.BikeRackResponseDTO;
import com.bikerackapp.bike_rack_service.controller.BikeRackController;
import com.bikerackapp.bike_rack_service.exception.ResourceNotFoundException;
import com.bikerackapp.bike_rack_service.model.BikeRack;
import com.bikerackapp.bike_rack_service.repository.BikeRackRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "bikeRacks")
    public List<BikeRackResponseDTO> getAllBikeRacks() {
        List<BikeRack> bikeRacks = bikeRackRepository.findAll();
        return bikeRacks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "bikeRacks", key = "#bikeRackId")
    public BikeRackResponseDTO getBikeRackById(UUID bikeRackId) {
        BikeRack bikeRack = bikeRackRepository.findById(bikeRackId)
                .orElseThrow(() -> new ResourceNotFoundException("Bike rack with ID " + bikeRackId + " not found"));
        return convertToDto(bikeRack);
    }

    @CachePut(cacheNames = "bikeRacks", key = "#bikeRackId")
    public BikeRackResponseDTO updateBikeRack(UUID bikeRackId, UpdateBikeRackRequestDTO bikeRackRequestDTO) {
        BikeRack bikeRack = bikeRackRepository.findById(bikeRackId)
                .orElseThrow(() -> new ResourceNotFoundException("Bike rack with ID " + bikeRackId + " not found"));
        bikeRack.setLatitude(bikeRackRequestDTO.latitude());
        bikeRack.setLongitude(bikeRackRequestDTO.longitude());
        bikeRack.setRating(bikeRackRequestDTO.rating());
        bikeRack.setTheftsInLastMonth(bikeRackRequestDTO.theftsInLastMonth());
        bikeRackRepository.save(bikeRack);
        LOGGER.info("Successfully updated bikeRack with ID: {}", bikeRackId);
        return convertToDto(bikeRack);
    }

    @CachePut(cacheNames = "bikeRacks", key = "#bikeRackId")
    public BikeRackResponseDTO updateRating(UUID bikeRackId, double newRating) {
        BikeRack bikeRack = bikeRackRepository.findById(bikeRackId)
                .orElseThrow(() -> new ResourceNotFoundException("Bike rack with ID " + bikeRackId + " not found"));
        int numRatings = bikeRack.getNumRatings();
        double totalRating = (bikeRack.getRating() * (double) numRatings);
        bikeRack.setRating((totalRating + newRating) / (double) (numRatings + 1));
        bikeRack.incrementNumRatings();
        bikeRackRepository.save(bikeRack);
        return convertToDto(bikeRack);
    }

    @CacheEvict(cacheNames = "bikeRacks", key = "#bikeRackId")
    public boolean deleteBikeRack(UUID bikeRackId) {
        bikeRackRepository.findById(bikeRackId)
                .orElseThrow(() -> new ResourceNotFoundException("Bike rack with ID " + bikeRackId + " not found"));
        bikeRackRepository.deleteById(bikeRackId);
        LOGGER.info("Successfully deleted bikeRack with ID: {}", bikeRackId);
        return true;
    }

    public void processMessage(String message) {
        LOGGER.info("Successfully received message from Reporting Service: {}", message);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(message);

            String reportType = rootNode.get("reportType").asText();
            if(reportType.equals("THEFT")) {
                bikeRackRepository.updateTheftsInLastMonth(
                        UUID.fromString(rootNode.get("bikeRackId").asText()),
                        rootNode.get("theftsInLastMonth").asInt()
                );
            } else if (reportType.equals("NEW_RACK")) {
                BikeRack bikeRack = new BikeRack(
                        rootNode.get("latitude").asDouble(),
                        rootNode.get("longitude").asDouble()
                );
                bikeRackRepository.save(bikeRack);
            } else if (reportType.equals("REMOVED_RACK")) {
                bikeRackRepository.deleteById(UUID.fromString(rootNode.get("bikeRackId").asText()));
            }
        } catch (Exception e) {
            LOGGER.error("Error decoding message from Reporting Service\nmessage: {}\nerror: {}", message, e.getMessage());
        }
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