package com.codelens.controller;

import com.codelens.MLServiceClient;
import com.codelens.model.CodeReview;
import com.codelens.model.CodeReviewRequest;
import com.codelens.repository.CodeReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
public class CodeReviewController {

    @Autowired
    private MLServiceClient mlServiceClient;

    @Autowired
    private CodeReviewRepository reviewRepository;

    @MessageMapping("/review")
    @SendTo("/topic/feedback")
    public Map<String, Object> reviewCode(CodeReviewRequest request) {
        String code = request.getCode();
        String language = request.getLanguage();

        Map<String, Object> response = new HashMap<>();
        response.put("language", language);
        response.put("lines", code.split("\n").length);
        response.put("complexity", detectComplexity(code));

        Map<String, Object> mlResult = mlServiceClient.analyzeCode(code, language);
        response.put("quality_score", mlResult.get("quality_score"));
        response.put("bugs", mlResult.get("bugs"));
        response.put("code_smells", mlResult.get("code_smells"));
        response.put("suggestions", mlResult.get("suggestions"));

        CodeReview review = new CodeReview();
        review.setSessionId(request.getSessionId());
        review.setLanguage(language);
        review.setCode(code);
        review.setQualityScore(String.valueOf(mlResult.get("quality_score")));
        review.setComplexity(detectComplexity(code));
        review.setBugs(String.valueOf(mlResult.get("bugs")));
        review.setSuggestions(String.valueOf(mlResult.get("suggestions")));
        reviewRepository.save(review);

        System.out.println("Review saved to PostgreSQL!");
        return response;
    }

    @GetMapping("/history/{sessionId}")
    public List<CodeReview> getHistory(@PathVariable String sessionId) {
        return reviewRepository.findBySessionIdOrderByCreatedAtDesc(sessionId);
    }

    private String detectComplexity(String code) {
        long loops = code.lines()
                .filter(line -> line.contains("for") || line.contains("while"))
                .count();
        if (loops >= 2) return "O(n²) — nested loops detected";
        if (loops == 1) return "O(n) — single loop detected";
        return "O(1) — no loops detected";
    }
}
