package com.bikerackapp.reporting.repository;

import com.bikerackapp.reporting.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository extends MongoRepository<Report, String> {

    List<Report> findByRackId(String rackId);

    @Query("{'rackId': ?0, 'reportType': ?1, 'createdAt':  { $gte: ?2, $lte:  ?3 }}")
    List<Report> findByRackIdAndReportTypeWithinDateRange(
            String rackId,
            Report.ReportType reportType,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    @Query("{ 'reportType': ?0, 'createdAt':  { $gte: ?1, $lte:  ?2 }, 'latitude':  { $gte: ?3, $lte:  ?4 }, 'longitude':  { $gte: ?5, $lte:  ?6 }}")
    List<Report> findByLatLongAndReportTypeWithinDateRange(
            Report.ReportType reportType,
            LocalDateTime startDate,
            LocalDateTime endDate,
            double latStart,
            double latEnd,
            double longStart,
            double longEnd
    );
}
