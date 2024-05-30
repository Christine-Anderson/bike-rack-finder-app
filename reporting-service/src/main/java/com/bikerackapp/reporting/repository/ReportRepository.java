package com.bikerackapp.reporting.repository;

import com.bikerackapp.reporting.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReportRepository extends MongoRepository<Report, UUID> {

    @Query("{'rackId': ?0, 'reportType': ?1, 'createdAt':  { $gte: ?0, $lte:  ?1 }}")
    List<Report> findByRackIdAndReportTypeWithinDateRange(
            String rackId,
            Report.ReportType reportType,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
