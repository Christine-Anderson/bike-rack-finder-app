package com.bikerackapp.reporting.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReportResponse(
        UUID id,
        UUID rackId,
        String reportType,
        String details,
        UUID userId,
        LocalDateTime createdAt
) {}