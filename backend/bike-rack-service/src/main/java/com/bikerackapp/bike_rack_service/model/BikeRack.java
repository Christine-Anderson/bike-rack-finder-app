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
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "latitude", nullable = false)
    private double latitude;
    @Column(name = "longitude", nullable = false)
    private double longitude;
    @Column(name = "rating", nullable = false)
    private double rating;
    @Column(name = "num_ratings", nullable = false)
    private int numRatings;
    @Column(name = "thefts_in_last_month", nullable = false)
    private int theftsInLastMonth;

    public BikeRack() {
    }

    public BikeRack(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = 0;
        this.numRatings = 0;
        this.theftsInLastMonth = 0;
    }

    public BikeRack(UUID rackId, String address, double latitude, double longitude, double rating, int numRatings, int theftsInLastMonth) {
        this.address = address;
        this.rackId = rackId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.theftsInLastMonth = theftsInLastMonth;
    }

    public UUID getRackId() {
        return rackId;
    }

    public String getAddress() {
        return address;
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

    public int getNumRatings() {
        return numRatings;
    }

    public int getTheftsInLastMonth() {
        return theftsInLastMonth;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void setTheftsInLastMonth(int theftsInLastMonth) {
        this.theftsInLastMonth = theftsInLastMonth;
    }

    public void incrementNumRatings() {
        numRatings++;
    }
}

