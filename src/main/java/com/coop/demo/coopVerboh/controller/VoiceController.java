package com.coop.demo.coopVerboh.controller;

import com.coop.demo.coopVerboh.service.TTSService;
import com.coop.demo.coopVerboh.service.VoiceEngineCoordinator;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/voice")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // dev only
public class VoiceController {

    @Value("${voice.welcome-text}")
    private String welcomeNote;

    private final VoiceEngineCoordinator coordinator;
    private final TTSService ttsService;

    @PostMapping(
            value = "/process",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,

            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<byte[]> process(
            @RequestParam("file") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            byte[] responseAudio = coordinator.handleAudio(file.getBytes());
            return ResponseEntity.ok(responseAudio);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
   /* Welcome note API */
    @GetMapping(value = "/welcome", produces = "audio/wav")
    public ResponseEntity<byte[]> welcome() {
        String welcomeText = welcomeNote;
        byte[] audio = ttsService.synthesizeSpeech(welcomeText);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/wav"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"welcome.wav\"")
                .body(audio);
    }
}
