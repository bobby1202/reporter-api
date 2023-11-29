package com.bank.reporter.service;

import com.bank.reporter.exception.ReporterException;
import com.bank.reporter.model.AggregatedData;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
