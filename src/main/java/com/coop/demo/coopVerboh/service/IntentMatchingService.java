package com.coop.demo.coopVerboh.service;

import com.coop.demo.coopVerboh.model.IntentMatchResult;
import com.coop.demo.coopVerboh.model.IntentPattern;
import com.coop.demo.coopVerboh.repository.IntentPatternRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
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
        log.info("Loaded {} intent patterns", cachedPatterns.size());
    }

    // Match incoming text to intent
    public IntentMatchResult matchIntent(String text) {
        log.info("The text supplied: {}", text);
        if (text == null || text.isBlank()) {
            return IntentMatchResult.unknown();
        }

        String normalized = text.trim(); // DO NOT lowercase regex

        for (IntentPattern pattern : cachedPatterns) {
            try {
                Pattern compiledPattern = Pattern.compile(
                        pattern.getPattern(),
                        Pattern.CASE_INSENSITIVE
                );

                if (compiledPattern.matcher(normalized).find()) {
                    log.info(
                            "Intent matched: text='{}' → intent='{}'",
                            normalized,
                            pattern.getIntent().getCode()
                    );

                    return new IntentMatchResult(
                            pattern.getIntent().getCode(),
                            1.0
                    );
                }

            } catch (Exception e) {
                log.error(
                        "Error matching regex pattern '{}': {}",
                        pattern.getPattern(),
                        e.getMessage()
                );
            }
        }

        log.info("No intent matched for text='{}'", normalized);
        return IntentMatchResult.unknown();
    }
}
