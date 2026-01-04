package com.coop.demo.coopVerboh.controller;

import com.coop.demo.coopVerboh.model.IntentMatchResult;
import com.coop.demo.coopVerboh.service.IntentMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/intent")
public class IntentController {

    private final IntentMatchingService intentMatchingService;

    public IntentController(IntentMatchingService intentMatchingService) {
        this.intentMatchingService = intentMatchingService;
    }

    @PostMapping("/match")
    public ResponseEntity<IntentMatchResult> matchIntent(@RequestParam("text") String text) {
        IntentMatchResult result = intentMatchingService.matchIntent(text);
        return ResponseEntity.ok(result);
    }
}
