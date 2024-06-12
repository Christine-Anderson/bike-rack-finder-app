package com.bikerackapp.reporting.service;

import com.bikerackapp.reporting.DTO.ReportRequestDTO;
import com.bikerackapp.reporting.DTO.ReportResponseDTO;
import com.bikerackapp.reporting.exception.ResourceNotFoundException;
import com.bikerackapp.reporting.model.Report;
import com.bikerackapp.reporting.controller.ReportController;
import com.bikerackapp.reporting.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
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
                newReport.userId()
        );

        if (newReport.reportType() == Report.ReportType.NEW_RACK) {
            report.setLatitude(newReport.latitude());
            report.setLongitude(newReport.longitude());
        }

        reportRepository.save(report);
        LOGGER.info("Successfully created report with ID: {}", report.getReportId());
        this.updateReportAggregation(report.getReportType(), report.getRackId());
        return convertToDto(report);
    }

    public List<ReportResponseDTO> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ReportResponseDTO getReportById(String reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report with ID " + reportId + " not found"));
        return convertToDto(report);
    }

    public ReportResponseDTO updateReport(String reportId, ReportResponseDTO reportRequestDTO) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report with ID " + reportId + " not found"));
        report.setRackId(reportRequestDTO.rackId());
        report.setReportType(reportRequestDTO.reportType());
        report.setDetails(reportRequestDTO.details());
        reportRepository.save(report);
        LOGGER.info("Successfully updated report with ID: {}", reportId);
        this.updateReportAggregation(report.getReportType(), report.getRackId());
        return convertToDto(report);
    }

    public boolean deleteReport(String reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report with ID " + reportId + " not found"));
        reportRepository.deleteById(reportId);
        LOGGER.info("Successfully deleted report with ID: {}", reportId);
        this.updateReportAggregation(report.getReportType(), report.getRackId());
        return true;
    }

    private ReportResponseDTO convertToDto(Report report) {
        return new ReportResponseDTO(
                report.getReportId(),
                report.getRackId(),
                report.getReportType(),
                report.getDetails(),
                report.getUserId(),
                report.getCreatedAt(),
                report.getLatitude(),
                report.getLongitude()
        );
    }

    private void updateReportAggregation(Report.ReportType reportType, String bikeRackId) {
        switch (reportType) {
            case THEFT:
                reportAggregationService.calculateRecentThefts(bikeRackId);
                break;
            case NEW_RACK:
                reportAggregationService.addBikeRack(bikeRackId);
                break;
            case REMOVED_RACK:
                reportAggregationService.removeBikeRack(bikeRackId);
                break;
        }
    }
}
