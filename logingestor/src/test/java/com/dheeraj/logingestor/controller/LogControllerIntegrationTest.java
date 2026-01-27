package com.dheeraj.logingestor.controller;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.dheeraj.logingestor.constant.LogConstant;
import com.dheeraj.logingestor.model.LogRequest;
import com.dheeraj.logingestor.model.LogSearchCriteriaRequest;
import com.dheeraj.logingestor.model.MetaData;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LogControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private com.dheeraj.logingestor.repo.LogRepository logRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @org.junit.jupiter.api.BeforeEach
    public void setup() {
        logRepository.deleteAll();
    }

    @Test
    public void testLogIngestionAndQuery() throws Exception {
        // 1. Ingest a log
        LogRequest logRequest = new LogRequest();
        logRequest.setLevel("error");
        logRequest.setMessage("Database connection failed");
        logRequest.setResourceId("server-1234");
        logRequest.setTimestamp(LocalDateTime.now());
        logRequest.setTraceId("abc-xyz-123");
        logRequest.setSpanId("span-456");
        logRequest.setCommit("5e5342f");
        logRequest.setMetadata(new MetaData("server-0987"));

        String logRequestJson = objectMapper.writeValueAsString(logRequest);

        mockMvc.perform(post("/v1/api/logs" + LogConstant.SAVE_LOG_DATA_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(logRequestJson))
                .andExpect(status().isCreated());

        // 2. Search for the log by level
        LogSearchCriteriaRequest searchRequest = new LogSearchCriteriaRequest();
        searchRequest.setLevel("error");

        String searchRequestJson = objectMapper.writeValueAsString(searchRequest);

        mockMvc.perform(post("/v1/api/logs" + LogConstant.SEARCH_LOGS_BY_CRITERIA_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(searchRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", is("Database connection failed")))
                .andExpect(jsonPath("$[0].resourceId", is("server-1234")));

        // 3. Find all logs
        mockMvc.perform(get("/v1/api/logs" + LogConstant.SEARCH_ALL_LOGS_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // assuming clean DB for test
                .andExpect(jsonPath("$[0].traceId", is("abc-xyz-123")));
    }
}
