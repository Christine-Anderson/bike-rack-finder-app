package com.bikerackapp.bike_rack_service.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "bike_racks")
public class BikeRack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "rack_id")
    private UUID rackId;
    @Column(name = "latitude", nullable = false)
    private double latitude;
    @Column(name = "longitude", nullable = false)
    private double longitude;
    @Column(name = "rating")
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

