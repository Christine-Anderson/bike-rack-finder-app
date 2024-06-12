package com.bikerackapp.reporting.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(value="report")
public class Report {
    public enum ReportType {
        THEFT,
        NEW_RACK,
        REMOVED_RACK
    }

    @Id
    private String reportId;

    @Field("rackId")
    private String rackId;

    @Field("reportType")
    private ReportType reportType;

    @Field("details")
    private String details;

    @Field("userId")
    private String userId;

    @Field("createdAt")
    private LocalDateTime createdAt;

    @Field("latitude")
    private Double latitude;

    @Field("longitude")
    private Double longitude;

    public Report() {
    }

    public Report(String rackId, ReportType reportType, String details, String userId) {
        reportId = UUID.randomUUID().toString();
        this.rackId = rackId;
        this.reportType = reportType;
        this.details = details;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }

    public Report(String rackId, ReportType reportType, String details, String userId, Double latitude, Double longitude) {
        reportId = UUID.randomUUID().toString();
        this.rackId = rackId;
        this.reportType = reportType;
        this.details = details;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Report(String reportId, String rackId, ReportType reportType, String details, String userId, LocalDateTime createdAt, Double latitude, Double longitude) {
        this.reportId = reportId;
        this.rackId = rackId;
        this.reportType = reportType;
        this.details = details;
        this.userId = userId;
        this.createdAt = createdAt;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getReportId() {
        return reportId;
    }

    public String getRackId() {
        return rackId;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public String getDetails() {
        return details;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setRackId(String rackId) {
        this.rackId = rackId;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
