package com.bikerackapp.reporting.repository;

import com.bikerackapp.reporting.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ReportRepository extends MongoRepository<Report, UUID> {
}
