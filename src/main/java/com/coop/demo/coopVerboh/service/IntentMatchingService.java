package com.coop.demo.coopVerboh.service;

import com.coop.demo.coopVerboh.model.IntentMatchResult;
import com.coop.demo.coopVerboh.model.IntentPattern;
import com.coop.demo.coopVerboh.repository.IntentPatternRepository;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class IntentMatchingService {

    private final IntentPatternRepository patternRepo;
    private List<IntentPattern> cachedPatterns;

    public IntentMatchingService(IntentPatternRepository patternRepo) {
        this.patternRepo = patternRepo;
    }

    // Load enabled patterns on startup to reduce DB hits
    @PostConstruct
    public void loadPatterns() {
        cachedPatterns = patternRepo.findAllEnabled();
    }

    // Match incoming text to intent
    public IntentMatchResult matchIntent(String text) {
        if (text == null || text.isBlank()) {
            return IntentMatchResult.unknown();
        }

        String normalized = text.toLowerCase().trim();

        for (IntentPattern pattern : cachedPatterns) {
            try {
                if (normalized.matches(pattern.getPattern().toLowerCase())) {
                    return new IntentMatchResult(pattern.getIntent().getCode(), 1.0);
                }
            } catch (Exception e) {
                // Log regex compilation/matching errors
                System.err.println("Error matching pattern: " + pattern.getPattern() + " -> " + e.getMessage());
            }
        }
        return IntentMatchResult.unknown();
    }
}
