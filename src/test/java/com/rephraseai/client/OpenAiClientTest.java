package com.rephraseai.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rephraseai.config.OpenAiProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpenAiClientTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private OpenAiClient openAiClient;

    @BeforeEach
    void setUp() {
        OpenAiProperties properties = new OpenAiProperties();
        properties.setApiKey("test-key");
        properties.setUrl("https://api.openai.com/v1/chat/completions");
        properties.setModel("gpt-4");
        properties.setPrompt(Map.of(
                "default", "You are a helpful assistant that rephrases text clearly."
        ));

        openAiClient = new OpenAiClient(restTemplate, objectMapper, properties);
    }

    @Test
    void callLlm_shouldReturnRephrasedText() throws Exception {
        String prompt = "Hello!";
        String mode = "default";
        String expected = "Hi!";

        String mockResponse = """
            {
              "choices": [
                {
                  "message": {
                    "content": "Hi!"
                  }
                }
              ]
            }
        """;

        ResponseEntity<String> response = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenReturn(response);

        String result = openAiClient.callLlm(mode, prompt);
        assertEquals(expected, result);
    }

    @Test
    void callLlm_shouldThrowExceptionOnFailure() {
        ResponseEntity<String> error = new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenReturn(error);

        Exception ex = assertThrows(RuntimeException.class, () ->
                openAiClient.callLlm("default", "Text")
        );

        assertTrue(ex.getMessage().contains("OpenAI API call failed"));
    }
}
