package com.bikerackapp.reporting.controller;

import com.bikerackapp.reporting.dto.ReportRequest;
import com.bikerackapp.reporting.dto.ReportResponse;
import com.bikerackapp.reporting.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    public ResponseEntity<List<ReportResponse>> getReports() {
        List<ReportResponse> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("report/{id}")
    public ResponseEntity<ReportResponse> getReportById(@PathVariable("id") UUID id) {
        ReportResponse report = reportService.getReportById(id);
        if (report != null) {
            return ResponseEntity.ok(report);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/report")
    public ResponseEntity<ReportResponse> createReport(@RequestBody ReportRequest reportRequest) {
        ReportResponse createdReport = reportService.createReport(reportRequest);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    @PutMapping("report/{id}")
    public ResponseEntity<ReportResponse> updateReport(@PathVariable("id") UUID id, @RequestBody ReportRequest reportRequest) {
        ReportResponse updatedReport = reportService.updateReport(id, reportRequest);
        if (updatedReport != null) {
            return ResponseEntity.ok(updatedReport);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("report/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable("id") UUID id) {
        boolean isDeleted = reportService.deleteReport(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
