package com.rephraseai.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rephraseai.dto.RephraseRequest;
import com.rephraseai.dto.RephraseResponse;
import com.rephraseai.service.RephraseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RephraseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RephraseService rephraseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnRephrasedTextWhenValidInputIsProvided() throws Exception {
        String inputText = "Hello world";
        String mode = "default";
        String rephrasedText = "Hi planet";

        when(rephraseService.rephrase(inputText, mode)).thenReturn(rephrasedText);

        RephraseRequest request = new RephraseRequest(inputText);
        RephraseResponse expectedResponse = new RephraseResponse(rephrasedText);

        mockMvc.perform(post("/api/v1/rephrase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void testRephraseEndpointHandlesMissingText() throws Exception {
        String requestBody = "{}";

        mockMvc.perform(post("/api/v1/rephrase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
