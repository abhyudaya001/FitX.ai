package com.fitness.aiservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();

    }

    public String getRecommendation(String question) {
        log.info("GEMINI API URL :"+ geminiApiUrl);
        log.info("GEMINI API KEY :"+ geminiApiKey);
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[]{
                                Map.of("text", question)
                        })
                }
        );

        String response = webClient.post()
                .uri(geminiApiUrl) // ONLY the URL
                .header("Content-Type", "application/json")
                .header("x-goog-api-key", geminiApiKey) // key goes HERE
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        return response;
    }
}