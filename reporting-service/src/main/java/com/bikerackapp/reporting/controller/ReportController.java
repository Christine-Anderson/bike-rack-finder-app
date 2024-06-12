package com.bikerackapp.reporting.controller;

import com.bikerackapp.reporting.DTO.ReportRequestDTO;
import com.bikerackapp.reporting.DTO.ReportResponseDTO;
import com.bikerackapp.reporting.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@Validated
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<ReportResponseDTO> createReport(@Validated @RequestBody ReportRequestDTO newReport) {
        ReportResponseDTO createdReport = reportService.createReport(newReport);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReportResponseDTO>> getReports() {
        List<ReportResponseDTO> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportResponseDTO> getReportById(@PathVariable("reportId") String reportId) {
        ReportResponseDTO report = reportService.getReportById(reportId);
        return ResponseEntity.ok(report);
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<ReportResponseDTO> updateReport(@PathVariable("reportId") String reportId, @RequestBody ReportResponseDTO reportToUpdate) {
        ReportResponseDTO updatedReport = reportService.updateReport(reportId, reportToUpdate);
        return ResponseEntity.ok(updatedReport);
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable("reportId") String reportId) {
        reportService.deleteReport(reportId);
        return ResponseEntity.noContent().build();
    }
}
