package com.bank.reporter.service;

import com.bank.reporter.model.AggregatedData;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.bank.reporter.util.DateUtil.convertStringToLocalDateTime;

@Service
public class ReporterCSVProcesser {

    /**
     * Processes a batch of incidents and updates the aggregated data map.
     *
     * @param csvBatch           Batch of incident data from the CSV file
     * @param aggregatedDataMap Map containing aggregated data
     */
    public void processBatchData(List<String[]> csvBatch, Map<String, AggregatedData> aggregatedDataMap) {
        csvBatch.stream()
                .forEach(row -> {
                    String assetName = row[0];
                    LocalDateTime startTime = convertStringToLocalDateTime(row[1]);
                    LocalDateTime endTime = convertStringToLocalDateTime(row[2]);
                    int severity = Integer.parseInt(row[3]);
                    //Calculate time difference between startTime and endTime in seconds
                    long durationInSeconds = new Duration(startTime.toDateTime(), endTime.toDateTime()).getStandardSeconds();
                    aggregatedDataMap.computeIfAbsent(assetName, k -> new AggregatedData())
                            .addIncident(severity, durationInSeconds);
                });
    }
}
