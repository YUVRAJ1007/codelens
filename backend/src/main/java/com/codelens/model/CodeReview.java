package com.codelens.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "code_reviews")
public class CodeReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;
    private String language;

    @Column(columnDefinition = "TEXT")
    private String code;

    private String qualityScore;
    private String complexity;

    @Column(columnDefinition = "TEXT")
    private String bugs;

    @Column(columnDefinition = "TEXT")
    private String suggestions;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getQualityScore() { return qualityScore; }
    public void setQualityScore(String qualityScore) { this.qualityScore = qualityScore; }

    public String getComplexity() { return complexity; }
    public void setComplexity(String complexity) { this.complexity = complexity; }

    public String getBugs() { return bugs; }
    public void setBugs(String bugs) { this.bugs = bugs; }

    public String getSuggestions() { return suggestions; }
    public void setSuggestions(String suggestions) { this.suggestions = suggestions; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
