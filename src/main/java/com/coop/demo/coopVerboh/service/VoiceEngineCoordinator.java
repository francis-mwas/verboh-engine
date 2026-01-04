package com.coop.demo.coopVerboh.service;

import com.coop.demo.coopVerboh.model.IntentMatchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoiceEngineCoordinator {

    private final IntentMatchingService intentMatchingService;
    private final STTService sttService;
    private final TTSService ttsService;

    public byte[] handleAudio(byte[] audioData) {
        // Convert audio -> text
        String text = sttService.transcribeAudio(audioData);

        // Match intent
        IntentMatchResult intentResult = intentMatchingService.matchIntent(text);

        // Process intent
        String responseText = processIntent(intentResult);

        // Convert response -> speech
        return ttsService.synthesizeSpeech(responseText);
    }

    private String processIntent(IntentMatchResult intentResult) {
        switch (intentResult.getIntentCode()) {
            case "CHECK_BALANCE":
                return "Your account balance is KES 23000";
            case "TRANSFER_FUNDS":
                return "Please provide recipient details to transfer funds";
            default:
                return "Sorry, I did not understand your request";
        }
    }
}
