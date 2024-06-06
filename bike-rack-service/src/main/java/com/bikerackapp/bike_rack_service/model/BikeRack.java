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
    private double rating; // todo rating determined by user votes?
    @Column(name = "thefts_in_last_month")
    private int theftsInLastMonth;

    public BikeRack() {
    }

    public BikeRack(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = 0;
        this.theftsInLastMonth = 0;
    }

    public BikeRack(double latitude, double longitude, double rating, int theftsInLastMonth) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.theftsInLastMonth = theftsInLastMonth;
    }

    public BikeRack(UUID rackId, double latitude, double longitude, double rating, int theftsInLastMonth) {
        this.rackId = rackId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.theftsInLastMonth = theftsInLastMonth;
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

    public int getTheftsInLastMonth() {
        return theftsInLastMonth;
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
}

