package com.bank.reporter.service;

import com.bank.reporter.model.AggregatedData;
import jdk.jfr.Description;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ReporterCSVProcesserTest {

    @InjectMocks
    private ReporterCSVProcesser reporterCSVProcesser;

    @Test
    @Description("To test while processing data")
    void testProcessBatchData() {
        MockitoAnnotations.initMocks(this);

        // Mock data for the test
        List<String[]> csvBatch = List.of(
                new String[]{"CRM", "4/1/2019 8:10", "4/1/2019 8:30", "1"},
                new String[]{"PaymentGateway", "4/1/2019 8:20", "4/1/2019 8:55", "2"}
        );

        Map<String, AggregatedData> aggregatedDataMap = new HashMap<>();

        reporterCSVProcesser.processBatchData(csvBatch, aggregatedDataMap);

        // Verify that the aggregatedDataMap is correctly updated
        assertEquals(2, aggregatedDataMap.size());
        assertEquals(1, aggregatedDataMap.get("CRM").getTotalIncidents());
        assertEquals(1200, aggregatedDataMap.get("CRM").getTotalDownTimeInSeconds());
        assertEquals(30, aggregatedDataMap.get("CRM").getRating());

        assertEquals(1, aggregatedDataMap.get("PaymentGateway").getTotalIncidents());
        assertEquals(0, aggregatedDataMap.get("PaymentGateway").getTotalDownTimeInSeconds());
        assertEquals(10, aggregatedDataMap.get("PaymentGateway").getRating());
    }
}
