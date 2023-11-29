package com.bank.reporter.service;

import com.bank.reporter.exception.ReporterException;
import com.bank.reporter.model.AggregatedData;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import jdk.jfr.Description;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReporterServiceImplTest {

    @Mock
    private CSVReader csvReader;

    @Mock
    private CSVWriter csvWriter;

    @Mock
    private ReporterCSVWriter reporterCSVWriter;

    @Mock
    private ReporterCSVProcesser reporterCSVProcesser;

    @InjectMocks
    private ReporterServiceImpl reporterService;

    private String csvInputFilePath = "src/test/resources/input.csv";
    private String csvOutputFilePath = "src/test/resources/output.csv";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(reporterService, "csvBatchSize", "1000");
    }

    @Test
    @Description("To test aggregated data")
    void testGenerateAggregatedReportData() throws IOException, CsvException {
        List<String[]> csvRows = new ArrayList<>();
        csvRows.add(new String[]{"Asset Name", "Start Date", "End Date", "Severity"});
        csvRows.add(new String[]{"CRM", "4/1/2019 8:10", "4/1/2019 8:30", "1"});
        csvRows.add(new String[]{"PaymentGateway", "4/1/2019 8:45", "4/1/2019 8:55", "2"});

        when(csvReader.readAll()).thenReturn(csvRows);
        doNothing().when(reporterCSVWriter).writeToCsv(any(), any());
        doNothing().when(reporterCSVProcesser).processBatchData(any(), any());

        reporterService.generateAggregatedReportData(csvInputFilePath, csvOutputFilePath);
        verify(reporterCSVWriter, times(1)).writeToCsv(any(), any());
        verify(reporterCSVProcesser, times(1)).processBatchData(any(), any());
    }

}
