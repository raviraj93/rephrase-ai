package com.rephraseai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "openai")
public class OpenAiProperties {

    private String apiKey;
    private String url;
    private String model;
    private Map<String, String> prompt;
    private Map<String, String> mode;


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Map<String, String> getPrompt() {
        return prompt;
    }

    public void setPrompt(Map<String, String> prompt) {
        this.prompt = prompt;
    }

    public Map<String, String> getMode() {
        return mode;
    }

    public void setMode(Map<String, String> mode) {
        this.mode = mode;
    }
}
