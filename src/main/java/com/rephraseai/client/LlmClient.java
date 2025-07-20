package com.rephraseai.client;

public interface LlmClient {
    String callLlm(String model, String prompt) throws Exception;
}