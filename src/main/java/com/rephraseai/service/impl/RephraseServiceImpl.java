package com.rephraseai.service.impl;

import com.rephraseai.client.LlmClient;
import com.rephraseai.service.RephraseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RephraseServiceImpl implements RephraseService {

    private final LlmClient llmClient;
    private final Map<String, String> promptMap;

    private static final Logger log = LoggerFactory.getLogger(RephraseServiceImpl.class);


    public RephraseServiceImpl(LlmClient llmClient,
                               @Value("${openai.prompt.default}") String defaultPrompt,
                               @Value("${openai.prompt.humanize}") String humanizePrompt,
                               @Value("${openai.prompt.playful}") String playfulPrompt) {
        this.llmClient = llmClient;
        this.promptMap = Map.of(
                "default", defaultPrompt,
                "humanize", humanizePrompt,
                "playful", playfulPrompt
        );
    }

    @Override
    public String rephrase(String text, String mode) {
        String prompt = promptMap.getOrDefault(mode, promptMap.get("default"));
        try {
            return llmClient.callLlm(prompt, text);
        } catch (Exception e) {
            log.error("Rephrasing failed for mode '{}'. Text: '{}'. Error: {}", mode, text, e.getMessage(), e);
            return "Error during rephrasing: " + (e.getMessage() != null ? e.getMessage() : "Unknown error");
        }
    }
}

