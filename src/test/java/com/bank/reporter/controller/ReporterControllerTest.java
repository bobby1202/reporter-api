package com.bank.reporter.controller;

import com.bank.reporter.service.ReporterService;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(ReporterController.class)
class ReporterControllerTest {

    @MockBean
    private ReporterService reporterService;

    @Autowired
    private MockMvc mockMvc;

    @Value("${csv.file.path.input}")
    private String csvInputFilePath;

    @Value("${csv.file.path.output}")
    private String csvOutputFilePath;

    @BeforeEach
    public void setUp() {
        doNothing().when(reporterService).generateAggregatedReportData(anyString(), anyString());
    }

    @Test
    @Description("To test Generate Incident Endpoint")
    void testGenerateIncidentData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/dashboard"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}