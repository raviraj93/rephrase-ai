package com.rephraseai.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rephraseai.RephraseAiApplication;
import com.rephraseai.dto.RephraseRequest;
import com.rephraseai.dto.RephraseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RephraseAiApplication.class)
@AutoConfigureMockMvc
class RephraseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRephraseEndpoint_withRealService() throws Exception {
        RephraseRequest request = new RephraseRequest("Hello world");

        String responseBody = mockMvc.perform(post("/api/v1/rephrase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        RephraseResponse response = objectMapper.readValue(responseBody, RephraseResponse.class);
        assertThat(response.rephrased()).isNotBlank();
        assertThat(response.rephrased()).isNotEqualTo("Hello world");
    }
}

