package com.rephraseai.provider;

import org.springframework.stereotype.Component;

@Component
public class PerplexityRephraser implements RephraseProvider {
    @Override
    public String rephrase(String text) throws Exception {
        return "Perplexity rephrased: " + text;
    }
}
