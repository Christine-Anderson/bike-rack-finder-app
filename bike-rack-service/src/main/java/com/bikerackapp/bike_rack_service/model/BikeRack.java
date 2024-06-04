package com.bikerackapp.bike_rack_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
@Table(name = "bike_racks")
public class BikeRack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID rackId;
    private double latitude;
    private double longitude;
    private double rating;

    public BikeRack() {
    }

    public BikeRack(double latitude, double longitude, double rating) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
    }

    public BikeRack(UUID rackId, double latitude, double longitude, double rating) {
        this.rackId = rackId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
    }

    public UUID getRackId() {
        return rackId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getRating() {
        return rating;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}

