package com.bikerackapp.reporting.service;

import com.bikerackapp.reporting.model.Report;
import com.bikerackapp.reporting.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportAggregationService {
    private static final int NUM_BIKE_RACK_CHANGE_REPORTS = 3;
    private static final int NUM_MONTHS = 1;

    private final ReportRepository reportRepository;
    private final MessageProducer messageProducer;

    public ReportAggregationService(ReportRepository reportRepository, MessageProducer messageProducer) {
        this.reportRepository = reportRepository;
        this.messageProducer = messageProducer;
    }

    public String calculateRecentThefts(String bikeRackId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime prevMonth = now.minusMonths(NUM_MONTHS);
        List<Report> theftReports = reportRepository.findByRackIdAndReportTypeWithinDateRange(
                bikeRackId,
                Report.ReportType.THEFT,
                prevMonth, now
        );
        int theftsInLastMonth = theftReports.size();
        String message = "THEFT, " + bikeRackId + "," + theftsInLastMonth;
        messageProducer.sendMessage(message);
        System.out.println(message);
        return message;
    }

    public String addBikeRack(String bikeRackId) { // todo debug
        double deltaDistLat = 0.00001;
        double deltaDistLong = 0.0001;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime prevMonth = now.minusMonths(NUM_MONTHS);
        Report newRackReport = reportRepository.findByRackId(bikeRackId).get(0);
        double latitude = newRackReport.getLatitude();
        double longitude = newRackReport.getLongitude();
        List<Report> bikeRackChangeReports = reportRepository.findByLatLongAndReportTypeWithinDateRange(
                Report.ReportType.NEW_RACK,
                prevMonth, now,
                latitude + deltaDistLat, latitude - deltaDistLat,
                longitude + deltaDistLong, longitude - deltaDistLong
        );
        System.out.println(bikeRackChangeReports);
        String message = "NEW_RACK, " + bikeRackId + ",";
        if (bikeRackChangeReports.size() >= NUM_BIKE_RACK_CHANGE_REPORTS) {
            messageProducer.sendMessage(message);
        }
        System.out.println(message);
        return message;
    }

    public String removeBikeRack(String bikeRackId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime prevMonth = now.minusMonths(NUM_MONTHS);
        List<Report> bikeRackChangeReports = reportRepository.findByRackIdAndReportTypeWithinDateRange(
                bikeRackId, Report.ReportType.REMOVED_RACK, prevMonth, now
        );
        String message = "REMOVED_RACK, " + bikeRackId;
        if (bikeRackChangeReports.size() >= NUM_BIKE_RACK_CHANGE_REPORTS) {
            messageProducer.sendMessage(message);
        }
        System.out.println(message);
        return message;
    }
}
