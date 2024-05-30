package com.bikerackapp.reporting.service;

import com.bikerackapp.reporting.dto.ReportResponse;
import com.bikerackapp.reporting.model.Report;
import com.bikerackapp.reporting.controller.ReportController;
import com.bikerackapp.reporting.repository.ReportRepository;
import com.bikerackapp.reporting.dto.ReportRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private static final String REPORT_NOT_FOUND_WARNING = "Report with ID: {} not found";
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    private final ReportRepository reportRepository;
    private final ReportAggregationService reportAggregationService;

    public ReportService(ReportRepository reportRepository, ReportAggregationService reportAggregationService) {
        this.reportRepository = reportRepository;
        this.reportAggregationService = reportAggregationService;
    }

    public ReportResponse createReport(ReportRequest reportRequest) {
        Report report = new Report(
                reportRequest.rackId(),
                reportRequest.reportType(),
                reportRequest.details(),
                reportRequest.userId(),
                reportRequest.createdAt()
        );
        reportRepository.save(report);
        LOGGER.info("Successfully created report with ID: {}", reportRequest.id());
        this.updateReportAggregation(report);
        return convertToDto(report);
    }

    public List<ReportResponse> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ReportResponse getReportById(UUID id) {
        Report report = reportRepository.findById(id).orElse(null);
        if (report != null) {
            return convertToDto(report);
        } else {
            return null;
        }
    }

    public ReportResponse updateReport(UUID id, ReportRequest reportRequest) {
       Report report = reportRepository.findById(id).orElse(null);
        if (report != null) {
            report.setRackId(reportRequest.rackId());
            report.setReportType(reportRequest.reportType());
            report.setDetails(reportRequest.details());
            report.setCreatedAt(reportRequest.createdAt());
            reportRepository.save(report);
            LOGGER.info("Successfully updated report with ID: {}", id);
            this.updateReportAggregation(report);
            return convertToDto(report);
        } else {
            LOGGER.warn(REPORT_NOT_FOUND_WARNING, id);
            return null;
        }
    }

    public boolean deleteReport(UUID id) {
        if (reportRepository.existsById(id)) {
            Report report = reportRepository.findById(id).orElse(null);
            reportRepository.deleteById(id);
            LOGGER.info("Successfully deleted report with ID: {}", id);
            this.updateReportAggregation(report);
            return true;
        } else {
            LOGGER.warn(REPORT_NOT_FOUND_WARNING, id);
            return false;
        }
    }

    private ReportResponse convertToDto(Report report) {
        return new ReportResponse(
                report.getId(),
                report.getRackId(),
                report.getReportType().name(),  // Convert enum to string
                report.getDetails(),
                report.getUserId(),
                report.getCreatedAt()
        );
    }

    private void updateReportAggregation(Report report) {
        if (report != null) {
            if (report.getReportType() != Report.ReportType.THEFT) {
                reportAggregationService.calculateRecentThefts(report.getRackId());
            } else {
                reportAggregationService.updateAvailableBikeRacks(report.getRackId(), report.getReportType());
            }
        }
    }
}
