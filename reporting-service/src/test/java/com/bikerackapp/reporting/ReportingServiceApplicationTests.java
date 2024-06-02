package com.bikerackapp.reporting;

import com.bikerackapp.reporting.model.Report;
import com.bikerackapp.reporting.repository.ReportRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ReportingServiceApplicationTests {

    @Container
    @ServiceConnection
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    ReportRepository reportRepository;

    private List<Report> reports;

    @LocalServerPort
    private Integer port;

    @BeforeAll
    static void beforeAll() {
        mongoDBContainer.start();
    }

	@BeforeEach
	void setup() {
        reportRepository.deleteAll();

        reports = new ArrayList<>();
        reports.add(new Report(
                UUID.fromString("9c9ecff0-0d2d-3299-a0b0-6b983f2c5dc0"),
                UUID.fromString("550e8400-e29b-41d4-a716-446655440001"),
                Report.ReportType.THEFT,
                "Bike stolen from rack.",
                UUID.fromString("750e8400-e29b-41d4-a716-446655440100"),
                LocalDateTime.of(2024, 5, 28, 14, 30, 0)
        ));
        reports.add(new Report(
                UUID.fromString("d7d4e8d8-2cb6-3226-b3b7-03b6d7de78f7"),
                UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),
                Report.ReportType.THEFT,
                "Bike stolen from another rack.",
                UUID.fromString("750e8400-e29b-41d4-a716-446655440102"),
                LocalDateTime.of(2024, 5, 28, 15, 30, 0)
        ));
        reports.add(new Report(
                UUID.fromString("ddf2c7a8-78cf-367e-910d-8438c1babb48"),
                UUID.fromString("550e8400-e29b-41d4-a716-446655440004"),
                Report.ReportType.NEW_RACK,
                "New rack added.",
                UUID.fromString("750e8400-e29b-41d4-a716-446655440104"),
                LocalDateTime.of(2024, 5, 28, 16, 30, 0)
        ));
        reports.add(new Report(
                UUID.fromString("2083e18c-4af5-32df-8399-b74bbf4850c5"),
                UUID.fromString("550e8400-e29b-41d4-a716-446655440006"),
                Report.ReportType.NEW_RACK,
                "Another new rack added.",
                UUID.fromString("750e8400-e29b-41d4-a716-446655440105"),
                LocalDateTime.of(2024, 5, 28, 17, 30, 0)
        ));
        reports.add(new Report(
                UUID.fromString("0c63be6d-df19-3d3d-a7e4-d73361aabf1f"),
                UUID.fromString("550e8400-e29b-41d4-a716-446655440008"),
                Report.ReportType.REMOVED_RACK,
                "Rack removed.",
                UUID.fromString("750e8400-e29b-41d4-a716-446655440106"),
                LocalDateTime.of(2024, 5, 28, 18, 30, 0)
        ));
        reports.add(new Report(
                UUID.fromString("e60fb4d5-bf84-3b6f-8dfd-e6e03d9fb7df"),
                UUID.fromString("550e8400-e29b-41d4-a716-446655440010"),
                Report.ReportType.REMOVED_RACK,
                "Another rack removed.",
                UUID.fromString("750e8400-e29b-41d4-a716-446655440107"),
                LocalDateTime.of(2024, 5, 28, 19, 30, 0)
        ));

        reportRepository.saveAll(reports);
	}

    @Test
    void givenGetAllReportsRequest_hasReports_thenReturnsAllReports() throws Exception {
        this.mockMvc.perform(get("/reports"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.size()", is(reports.size())));
    }

    @Test
    void givenGetReportByIdRequest_whenValidId_thenReturnsReport() throws Exception {
        Report expectedReport = reports.get(0);
        String reportId = expectedReport.getReportId().toString();

        this.mockMvc.perform(get("/report/{id}", reportId))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.reportId", is(reportId)))
                .andExpect(jsonPath("$.rackId", is(expectedReport.getRackId().toString())))
                .andExpect(jsonPath("$.reportType", is(expectedReport.getReportType().toString())))
                .andExpect(jsonPath("$.details", is(expectedReport.getDetails())))
                .andExpect(jsonPath("$.userId", is(expectedReport.getUserId().toString())))
                .andExpect(jsonPath("$.createdAt", is(expectedReport.getCreatedAt().toString() + ":00")));
    }

    @Test
    void givenGetReportByIdRequest_whenInvalidId_thenReturnsNotFound() throws Exception {
        String reportId = "00000000-0000-0000-0000-000000000000";

        this.mockMvc.perform(get("/report/{id}", reportId))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

	@Test
	void givenCreateReportRequest_whenValidReport_thenReturnsCreatedReport() throws Exception {
        String newReport = """
                {
                  "rackId": "550e8400-e29b-41d4-a716-446655440000",
                  "reportType": "REMOVED_RACK",
                  "details": "Rack removed.",
                  "userId": "750e8400-e29b-41d4-a716-446655440103",
                  "createdAt": "2024-05-28T18:30:00"
                }
                """;

        Report expectedReport = new Report(
                UUID.randomUUID(),
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                Report.ReportType.REMOVED_RACK,
                "Rack removed.",
                UUID.fromString("750e8400-e29b-41d4-a716-446655440103"),
                LocalDateTime.of(2024, 5, 28, 18, 30, 0)
        );

        this.mockMvc.perform(post("/report")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newReport))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.rackId", is(expectedReport.getRackId().toString())))
                .andExpect(jsonPath("$.reportType", is(expectedReport.getReportType().toString())))
                .andExpect(jsonPath("$.details", is(expectedReport.getDetails())))
                .andExpect(jsonPath("$.userId", is(expectedReport.getUserId().toString())))
                .andExpect(jsonPath("$.createdAt", is(expectedReport.getCreatedAt().toString() + ":00")));
	}

    @Test
    void giveUpdateReportRequest_whenValidReport_thenReturnsUpdatedReport() throws Exception {
        String updatedReport = """
                {
                  "rackId": "550e8400-e29b-41d4-a716-446655440001",
                  "reportType": "THEFT",
                  "details": "Updated details",
                  "userId": "750e8400-e29b-41d4-a716-446655440100",
                  "createdAt": "2024-05-30T14:30:00"
                }
                """;

        Report expectedReport = reports.get(0);
        String reportId = expectedReport.getReportId().toString();
        expectedReport.setDetails("Updated details");
        expectedReport.setCreatedAt(LocalDateTime.of(2024, 5, 30, 14, 30, 0));

        this.mockMvc.perform(put("/report/{reportId}", reportId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedReport))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.reportId", is(reportId)))
                .andExpect(jsonPath("$.rackId", is(expectedReport.getRackId().toString())))
                .andExpect(jsonPath("$.reportType", is(expectedReport.getReportType().toString())))
                .andExpect(jsonPath("$.details", is(expectedReport.getDetails())))
                .andExpect(jsonPath("$.userId", is(expectedReport.getUserId().toString())))
                .andExpect(jsonPath("$.createdAt", is(expectedReport.getCreatedAt().toString() + ":00")));

    }

    @Test
    void givenDeleteReportRequest_whenValidReport_thenReturnsNoContent() throws Exception {
        Report expectedReport = reports.get(0);
        String reportId = expectedReport.getReportId().toString();

        this.mockMvc.perform(delete("/report/{reportId}", reportId))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));

        this.mockMvc.perform(get("/reports"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.size()", is(reports.size() - 1)));
    }

    @Test
    void givenDeleteReportRequest_whenInvalidId_thenReturnsNotFound() throws Exception {
        String reportId = "123e4567-e89b-12d3-a456-426614174000";

        this.mockMvc.perform(delete("/report/{reportId}", reportId))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }
}
