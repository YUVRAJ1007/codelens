package com.codelens;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
public class MLServiceClient {

    private final WebClient webClient;

    public MLServiceClient() {
        String mlServiceUrl = System.getenv("ML_SERVICE_URL") != null
            ? System.getenv("ML_SERVICE_URL")
            : "http://localhost:5001";
        System.out.println("ML Service URL: " + mlServiceUrl);
        this.webClient = WebClient.builder()
                .baseUrl(mlServiceUrl)
                .build();
    }

    @Cacheable(value = "codeReviews", key = "#code.hashCode()")
    public Map<String, Object> analyzeCode(String code, String language) {
        System.out.println("Calling ML service at: " + webClient);
        try {
            return webClient.post()
                    .uri("/analyze")
                    .bodyValue(Map.of("code", code, "language", language))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (Exception e) {
            System.out.println("ML service error: " + e.getMessage());
            return Map.of(
                "error", "ML service unavailable",
                "bugs", "Could not connect to AI engine",
                "suggestions", "Make sure Python service is running on port 5001"
            );
        }
    }
}
