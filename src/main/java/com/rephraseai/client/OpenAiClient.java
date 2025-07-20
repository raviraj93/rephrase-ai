package com.rephraseai.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rephraseai.config.OpenAiProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class OpenAiClient implements LlmClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final OpenAiProperties properties;

    public OpenAiClient(RestTemplate restTemplate, ObjectMapper objectMapper, OpenAiProperties properties) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.properties = properties;
    }

    @Override
    public String callLlm(String mode, String prompt) throws Exception {
        String model = properties.getModel();
        String apiUrl = properties.getUrl();
        String apiKey = properties.getApiKey();
        String systemPrompt = properties.getPrompt().getOrDefault(mode, properties.getPrompt().get("default"));

        HttpEntity<Map<String, Object>> request = buildRequest(model, prompt, systemPrompt, apiKey);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
        validateResponse(response);
        return extractContent(response.getBody());
    }

    private HttpEntity<Map<String, Object>> buildRequest(String model, String prompt, String systemPrompt, String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", prompt)
                )
        );

        return new HttpEntity<>(requestBody, headers);
    }

    private void validateResponse(ResponseEntity<String> response) {
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("OpenAI API call failed with status: " + response.getStatusCode());
        }
    }

    private String extractContent(String responseBody) throws Exception {
        JsonNode root = objectMapper.readTree(responseBody);
        JsonNode choices = root.path("choices");

        if (!choices.isArray() || choices.isEmpty()) {
            throw new RuntimeException("Invalid response from OpenAI: 'choices' is empty or missing");
        }

        JsonNode message = choices.get(0).path("message");
        String content = message.path("content").asText(null);

        if (content == null || content.isBlank()) {
            throw new RuntimeException("Invalid response from OpenAI: 'content' is missing or blank");
        }

        return content.trim();
    }
}
