package com.bikerackapp.reporting.DTO;

import com.bikerackapp.reporting.model.Report;
import jakarta.validation.constraints.*;

public record ReportRequestDTO(
        String rackId,

        @NotNull(message = "Report type is required")
        Report.ReportType reportType,

        @Size(max = 255)
        String details,

        @NotNull(message = "User Id type is required")
        String userId,

        @Size(max = 255, message = "Address length must at most 255 characters")
        String address,

        @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
        @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
        Double latitude,

        @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
        @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
        Double longitude
) {}