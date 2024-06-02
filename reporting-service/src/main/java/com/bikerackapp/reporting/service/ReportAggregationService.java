package com.bikerackapp.reporting.service;

import com.bikerackapp.reporting.model.Report;
import com.bikerackapp.reporting.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReportAggregationService {
    private static final int NUM_BIKE_RACK_CHANGE_REPORTS = 3;

    private final ReportRepository reportRepository;

    public ReportAggregationService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public int calculateRecentThefts(UUID rackId) { // todo tests after this is sorted out
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime prevMonth = now.minusMonths(1);
        List<Report> theftReports = reportRepository.findByRackIdAndReportTypeWithinDateRange(rackId.toString(), Report.ReportType.THEFT, prevMonth, now);
        int theftsInLastMonth = theftReports.size();
        // todo figure out how to send updated value to bike rack service
        return theftsInLastMonth;
    }

    public void updateAvailableBikeRacks(UUID rackId, Report.ReportType reportType) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime prevMonth = now.minusMonths(1);
        List<Report> bikeRackChangeReports = reportRepository.findByRackIdAndReportTypeWithinDateRange(rackId.toString(), reportType, prevMonth, now);

        if (reportType == Report.ReportType.NEW_RACK && bikeRackChangeReports.size() >= NUM_BIKE_RACK_CHANGE_REPORTS) {
            // todo add rack to bike rack service
        } else if (reportType == Report.ReportType.REMOVED_RACK && bikeRackChangeReports.size() >= NUM_BIKE_RACK_CHANGE_REPORTS) {
            // todo remove rack from bike rack service
        }
    }
}
