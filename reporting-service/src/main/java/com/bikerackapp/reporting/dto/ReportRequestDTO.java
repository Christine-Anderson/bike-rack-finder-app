package com.bikerackapp.reporting.DTO;

import com.bikerackapp.reporting.model.Report;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ReportRequestDTO(
        @NotEmpty(message = "Bike rack Id is required")
        UUID rackId,

        @NotEmpty(message = "Report type is required")
        Report.ReportType reportType,

        @Size(max = 255)
        String details,

        @NotEmpty(message = "User Id type is required")
        UUID userId
) {}