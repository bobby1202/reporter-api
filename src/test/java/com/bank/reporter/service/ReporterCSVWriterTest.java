package com.bank.reporter.service;

import com.bank.reporter.model.AggregatedData;
import com.opencsv.CSVWriter;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ReporterCSVWriterTest {

    @Mock
    private CSVWriter csvWriter;

    @InjectMocks
    ReporterCSVWriter reporterCSVWriter;

    @Test
    @Description("To test while writing to csv file")
    public void testWriteToCsv() {

        List<String[]> csvRows = new ArrayList<>();
        csvRows.add(new String[]{"Asset Name", "Total Incidents", "Total Downtime", "Rating"});
        csvRows.add(new String[]{"CRM", "5", "99", "150"});

        doNothing().when(csvWriter).writeNext(csvRows.get(0));
        doNothing().when(csvWriter).writeNext(csvRows.get(1));

        Map<String, AggregatedData> aggregatedDataMap = new HashMap<>();
        AggregatedData aggregatedData = new AggregatedData();
        aggregatedData.setTotalIncidents(5);
        aggregatedData.setTotalDownTimeInSeconds(95);
        aggregatedData.setRating(150);
        aggregatedDataMap.put("CRM", aggregatedData);

        reporterCSVWriter.writeToCsv(csvWriter, aggregatedDataMap);
        verify(csvWriter).writeNext(csvRows.get(1));
    }
}
