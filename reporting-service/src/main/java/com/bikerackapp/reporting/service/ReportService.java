package com.bikerackapp.reporting.service;

import com.bikerackapp.reporting.DTO.ReportRequestDTO;
import com.bikerackapp.reporting.DTO.ReportResponseDTO;
import com.bikerackapp.reporting.model.Report;
import com.bikerackapp.reporting.controller.ReportController;
import com.bikerackapp.reporting.repository.ReportRepository;
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

    @Autowired
    public ReportService(ReportRepository reportRepository, ReportAggregationService reportAggregationService) {
        this.reportRepository = reportRepository;
        this.reportAggregationService = reportAggregationService;
    }

    public ReportResponseDTO createReport(ReportRequestDTO newReport) {
        Report report = new Report(
                newReport.rackId(),
                newReport.reportType(),
                newReport.details(),
                newReport.userId(),
                newReport.createdAt()
        );
        reportRepository.save(report);
        LOGGER.info("Successfully created report with ID: {}", report.getReportId());
        this.updateReportAggregation(report);
        return convertToDto(report);
    }

    public List<ReportResponseDTO> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ReportResponseDTO getReportById(UUID reportId) {
        Report report = reportRepository.findById(reportId).orElse(null);
        if (report != null) {
            return convertToDto(report);
        } else {
            return null;
        }
    }

    public ReportResponseDTO updateReport(UUID reportId, ReportResponseDTO reportRequestDTO) {
       Report report = reportRepository.findById(reportId).orElse(null);
        if (report != null) {
            report.setRackId(reportRequestDTO.rackId());
            report.setReportType(reportRequestDTO.reportType());
            report.setDetails(reportRequestDTO.details());
            report.setCreatedAt(reportRequestDTO.createdAt());
            reportRepository.save(report);
            LOGGER.info("Successfully updated report with ID: {}", reportId);
            this.updateReportAggregation(report);
            return convertToDto(report);
        } else {
            LOGGER.warn(REPORT_NOT_FOUND_WARNING, reportId);
            return null;
        }
    }

    public boolean deleteReport(UUID reportId) {
        if (reportRepository.existsById(reportId)) {
            Report report = reportRepository.findById(reportId).orElse(null);
            reportRepository.deleteById(reportId);
            LOGGER.info("Successfully deleted report with ID: {}", reportId);
            this.updateReportAggregation(report);
            return true;
        } else {
            LOGGER.warn(REPORT_NOT_FOUND_WARNING, reportId);
            return false;
        }
    }

    private ReportResponseDTO convertToDto(Report report) {
        return new ReportResponseDTO(
                report.getReportId(),
                report.getRackId(),
                report.getReportType(),
                report.getDetails(),
                report.getUserId(),
                report.getCreatedAt()
        );
    }

    private void updateReportAggregation(Report report) {
        if (report != null) {
            if (report.getReportType() == Report.ReportType.THEFT) {
                reportAggregationService.calculateRecentThefts(report.getRackId());
            } else {
                reportAggregationService.updateAvailableBikeRacks(report.getRackId(), report.getReportType());
            }
        }
    }
}
