package com.rephraseai.provider;

import com.rephraseai.client.LlmClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChatGptRephraser implements RephraseProvider {

    private final LlmClient llmClient;

    @Value("${openai.model}")
    private String model;

    public ChatGptRephraser(LlmClient llmClient) {
        this.llmClient = llmClient;
    }

    @Override
    public String rephrase(String text) throws Exception {
        return llmClient.callLlm(model, text);
    }
}
