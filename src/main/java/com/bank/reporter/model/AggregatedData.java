package com.bank.reporter.model;

import lombok.Data;

/**
 * Aggregated data
 */
@Data
public class AggregatedData {
    private String assetName;
    private int totalIncidents;
    private long totalDownTimeInSeconds;
    private int rating;

    /**
     * Adds an incident to the aggregated data, updating relevant fields.
     *
     * @param severity           Severity of the incident (1 for severe, others for non-severe)
     * @param durationInSeconds  Duration of the incident in seconds
     */
    public void addIncident(int severity, long durationInSeconds) {
        totalIncidents++;
        int weight = (severity == 1) ? 30 : 10;
        totalDownTimeInSeconds += (severity == 1) ? durationInSeconds : 0;
        rating += weight;
    }

    /**
     * Retrieves the total number of incidents.
     *
     * @return Total number of incidents
     */
    public int getTotalIncidents() {
        return totalIncidents;
    }

    /**
     * Calculates and returns the uptime percentage based on total downtime.
     *
     * @return Uptime percentage
     */
    public long calculateUptimePercentage() {
        long totalSecondsInDay = 24 * 60 * 60;
        long totalUpTimeInSeconds = totalSecondsInDay - totalDownTimeInSeconds;
        return (totalUpTimeInSeconds * 100) / totalSecondsInDay;
    }

}
