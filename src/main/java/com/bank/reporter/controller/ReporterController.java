package com.bank.reporter.controller;

import com.bank.reporter.service.ReporterService;
import com.bank.reporter.service.ReporterServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class responsible for handling requests related to the reporting functionality.
 */
@Slf4j
@RestController
public class ReporterController {

    @Value("${csv.file.path.input}")
    private String csvInputFilePath;

    @Value("${csv.file.path.output}")
    private String csvOutputFilePath;

    private final ReporterService reporterService;

    @Autowired
    public ReporterController(ReporterService reporterService){
        this.reporterService = reporterService;
    }

    /**
     * Endpoint to generate aggregated incidents report.
     *
     * @return ResponseEntity indicating the success of the operation
     */
    @PostMapping("/api/dashboard")
    public ResponseEntity<Void> generateIncidentData(){
        log.info("Incident data processing started");
        reporterService.generateAggregatedReportData(csvInputFilePath, csvOutputFilePath);
        log.info("Incident data processing completed!!");
        return ResponseEntity.noContent().build();
    }
}
