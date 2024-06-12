package com.bikerackapp.reporting.service;

import com.bikerackapp.reporting.controller.ReportController;
import com.bikerackapp.reporting.model.Report;
import com.bikerackapp.reporting.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportAggregationService {
    private static final int NUM_BIKE_RACK_CHANGE_REPORTS = 3;
    private static final int NUM_MONTHS = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

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
        String message = "{ " +
                "\"reportType\": \"THEFT\", " +
                "\"bikeRackId\": " + "\"" + bikeRackId + "\"" + "," +
                "\"theftsInLastMonth\": " + "\"" + theftReports.size() + "\"" +
                " }";
        messageProducer.sendMessage(message);
        LOGGER.info("Sent message to Bike Rack Service: {}", message);
        System.out.println("Sent message to Bike Rack Service: " + message);
        return message;
    }

    public String addBikeRack(String reportId) {
        Double deltaDistLat = 0.00001;
        Double deltaDistLong = 0.0001;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime prevMonth = now.minusMonths(NUM_MONTHS);
        Report newRackReport = reportRepository.findByReportId(reportId).getFirst();
        Double latitude = newRackReport.getLatitude();
        Double longitude = newRackReport.getLongitude();

        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Latitude and Longitude must not be null for NEW_RACK reports");
        }

        List<Report> bikeRackChangeReports = reportRepository.findByLatLongAndReportTypeWithinDateRange(
                Report.ReportType.NEW_RACK,
                prevMonth, now,
                latitude - deltaDistLat, latitude + deltaDistLat,
                longitude - deltaDistLong, longitude + deltaDistLong
        );
        String message = "{ " +
                "\"reportType\": \"NEW_RACK\", " +
                "\"latitude\": " + "\"" + latitude + "\"" + "," +
                "\"longitude\": " + "\"" + longitude + "\"" +
                " }";
        if (bikeRackChangeReports.size() >= NUM_BIKE_RACK_CHANGE_REPORTS) {
            messageProducer.sendMessage(message);
            LOGGER.info("Sent message to Bike Rack Service: {}", message);
        }
        return message;
    }

    public String removeBikeRack(String bikeRackId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime prevMonth = now.minusMonths(NUM_MONTHS);
        List<Report> bikeRackChangeReports = reportRepository.findByRackIdAndReportTypeWithinDateRange(
                bikeRackId, Report.ReportType.REMOVED_RACK, prevMonth, now
        );
        String message = "{" +
                "\"reportType\": \"REMOVED_RACK\"," +
                "\"bikeRackId\": " + "\"" + bikeRackId + "\"" +
                " }";
        if (bikeRackChangeReports.size() >= NUM_BIKE_RACK_CHANGE_REPORTS) {
            messageProducer.sendMessage(message);
            LOGGER.info("Sent message to Bike Rack Service: {}", message);
        }
        return message;
    }
}
