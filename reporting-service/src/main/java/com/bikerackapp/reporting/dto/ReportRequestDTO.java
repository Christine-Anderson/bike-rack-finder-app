package com.bikerackapp.reporting.dto;

import com.bikerackapp.reporting.model.Report;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReportRequestDTO(
        UUID reportId,
        UUID rackId,
        Report.ReportType reportType,
        String details,
        UUID userId,
        LocalDateTime createdAt
) {}
