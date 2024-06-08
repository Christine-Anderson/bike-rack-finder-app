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
import java.util.UUID;

@RestController
@RequestMapping("/report")
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
    public ResponseEntity<ReportResponseDTO> getReportById(@PathVariable("reportId") UUID reportId) {
        ReportResponseDTO report = reportService.getReportById(reportId);
        if (report != null) {
            return ResponseEntity.ok(report);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<ReportResponseDTO> updateReport(@PathVariable("reportId") UUID reportId, @RequestBody ReportResponseDTO reportToUpdate) {
        ReportResponseDTO updatedReport = reportService.updateReport(reportId, reportToUpdate);
        if (updatedReport != null) {
            return ResponseEntity.ok(updatedReport);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable("reportId") UUID reportId) {
        boolean isDeleted = reportService.deleteReport(reportId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
