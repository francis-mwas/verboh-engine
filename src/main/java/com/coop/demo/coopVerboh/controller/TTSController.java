package com.coop.demo.coopVerboh.controller;

import com.coop.demo.coopVerboh.service.TTSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tts")
@RequiredArgsConstructor
public class TTSController {

    private final TTSService ttsService;

    @PostMapping(value = "/synthesize", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> synthesize(@RequestParam("text") String text) {
        byte[] audio = ttsService.synthesizeSpeech(text);
        return ResponseEntity.ok(audio);
    }
}
