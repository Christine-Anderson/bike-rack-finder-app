package com.bikerackapp.reporting.DTO;

import com.bikerackapp.reporting.model.Report;

import java.time.LocalDateTime;

public record ReportResponseDTO(
        String reportId,
        String rackId,
        Report.ReportType reportType,
        String details,
        String userId,
        LocalDateTime createdAt,
        String address,
        Double latitude,
        Double longitude
) {}