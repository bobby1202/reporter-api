package com.bank.reporter.service;

import com.bank.reporter.exception.ReporterException;
import com.bank.reporter.model.AggregatedData;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bank.reporter.util.DateUtil.convertStringToLocalDateTime;

@Slf4j
@Service
public class ReporterServiceImpl implements ReporterService{

    @Value("${csv.file.batchSize}")
    private String csvBatchSize;

    private final ReporterCSVWriter reporterCSVWriter;

    private final ReporterCSVProcesser reporterCSVProcesser;

    @Autowired
    public ReporterServiceImpl(ReporterCSVWriter reporterCSVWriter, ReporterCSVProcesser reporterCSVProcesser){
        this.reporterCSVProcesser = reporterCSVProcesser;
        this.reporterCSVWriter = reporterCSVWriter;
    }

    /**
     * Generates aggregated report data by processing incidents from CSV file.
     *
     * @param csvInputFilePath
     * @param csvOutputFilePath
     */
    public void generateAggregatedReportData(String csvInputFilePath, String csvOutputFilePath)  {

        Map<String, AggregatedData> aggregatedDataMap = new HashMap<>();
        int batchSize = Integer.parseInt(csvBatchSize); //Batch size
        try (CSVReader csvReader = new CSVReader(new FileReader(csvInputFilePath));
             CSVWriter csvWriter = new CSVWriter(new FileWriter(csvOutputFilePath)))
        {
            List<String[]> csvReaderRows = csvReader.readAll();
            int totalRows = csvReaderRows.size();//Total Rows of CSV File
            int processedRows = 1;
            while (processedRows < totalRows) {
                int endIndex = Math.min(processedRows + batchSize, totalRows);
                List<String[]> currentCSVBatch = csvReaderRows.subList(processedRows, endIndex);

                //processBatchData(currentCSVBatch, aggregatedDataMap);
                reporterCSVProcesser.processBatchData(currentCSVBatch, aggregatedDataMap);
                processedRows += batchSize;
            }
            log.info("Writing aggregated data to CSV File..");
            reporterCSVWriter.writeToCsv(csvWriter, aggregatedDataMap);
        } catch (CsvException | IOException e) {
            log.error("Error while processing CSV file:{}", e);
            throw new ReporterException(e.getMessage(), e);
        }
    }

    /**
     * Processes a batch of incidents and updates the aggregated data map.
     *
     * @param csvBatch           Batch of incident data from the CSV file
     * @param aggregatedDataMap Map containing aggregated data
     */
//    private void processBatchData(List<String[]> csvBatch, Map<String, AggregatedData> aggregatedDataMap) {
//        csvBatch.stream()
//                .forEach(row -> {
//                    String assetName = row[0];
//                    LocalDateTime startTime = convertStringToLocalDateTime(row[1]);
//                    LocalDateTime endTime = convertStringToLocalDateTime(row[2]);
//                    int severity = Integer.parseInt(row[3]);
//                    //Calculate time difference between startTime and endTime in seconds
//                    long durationInSeconds = new Duration(startTime.toDateTime(), endTime.toDateTime()).getStandardSeconds();
//                    aggregatedDataMap.computeIfAbsent(assetName, k -> new AggregatedData())
//                            .addIncident(severity, durationInSeconds);
//                });
//    }
}
