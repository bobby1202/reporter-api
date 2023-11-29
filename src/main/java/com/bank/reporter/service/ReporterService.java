package com.bank.reporter.service;

public interface ReporterService {

    /**
     *
     * @param csvInputFilePath
     * @param csvOutputFilePath
     */
    void generateAggregatedReportData(String csvInputFilePath, String csvOutputFilePath);
}
