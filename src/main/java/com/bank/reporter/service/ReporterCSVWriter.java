package com.bank.reporter.service;

import com.bank.reporter.model.AggregatedData;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class ReporterCSVWriter {
    private final String[] header = {"Asset Name", "Total Incidents", "Total Downtime", "Rating"};

    /**
     * Writes aggregated data to a CSV file.
     *
     * @param csvWriter         CSVWriter for writing to the output file
     * @param aggregatedDataMap Map containing aggregated data
     */
    public void writeToCsv(CSVWriter csvWriter, Map<String, AggregatedData> aggregatedDataMap) {
        csvWriter.writeNext(header);
        log.info("Added CSV Headers:{}",header);
        for (Map.Entry<String, AggregatedData> entryMap : aggregatedDataMap.entrySet()) {
            String assetName = entryMap.getKey();
            AggregatedData aggregatedData = entryMap.getValue();
            String[] line = {
                    assetName,
                    String.valueOf(aggregatedData.getTotalIncidents()),
                    String.valueOf(aggregatedData.calculateUptimePercentage()),
                    String.valueOf(aggregatedData.getRating())
            };
            csvWriter.writeNext(line);
        }
    }
}
