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
    private UUID reportId;

    @Field("rackId")
    private UUID rackId;

    @Field("reportType")
    private ReportType reportType;

    @Field("details")
    private String details;

    @Field("userId")
    private UUID userId;

    @Field("createdAt")
    private LocalDateTime createdAt;

    public Report() {
    }

    public Report(UUID rackId, ReportType reportType, String details, UUID userId, LocalDateTime createdAt) {
        reportId = UUID.randomUUID();
        this.rackId = rackId;
        this.reportType = reportType;
        this.details = details;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public Report(UUID reportId, UUID rackId, ReportType reportType, String details, UUID userId, LocalDateTime createdAt) {
        this.reportId = reportId;
        this.rackId = rackId;
        this.reportType = reportType;
        this.details = details;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public UUID getReportId() {
        return reportId;
    }

    public UUID getRackId() {
        return rackId;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public String getDetails() {
        return details;
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setRackId(UUID rackId) {
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
}
