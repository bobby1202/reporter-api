package com.bank.reporter.cron;

import com.bank.reporter.service.ReporterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReporterCronJob {

    @Value("${csv.file.path.input}")
    private String csvInputFilePath;

    @Value("${csv.file.path.output}")
    private String csvOutputFilePath;

    private final ReporterService reporterService;

    @Autowired
    public ReporterCronJob(ReporterService reporterService){
        this.reporterService = reporterService;
    }
    @Scheduled(cron = "0 0 4 * * ?", zone = "America/New_York")
    public void processCsvFile() {
        log.info("CSV processing job started at 4:00 AM EST");
        reporterService.generateAggregatedReportData(csvInputFilePath, csvOutputFilePath);
        log.info("CSV processing job completed!!");
    }
}