package com.coop.demo.coopVerboh.controller;

import com.coop.demo.coopVerboh.dtos.STTResponse;
import com.coop.demo.coopVerboh.service.STTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/stt")
@RequiredArgsConstructor
public class STTController {

    private final STTService sttService;

    @PostMapping(
            value = "/transcribe",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<STTResponse> transcribe(
            @RequestParam("file") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            byte[] audioBytes = file.getBytes();  
            String text = sttService.transcribeAudio(audioBytes);
            log.info("The text is {}", text);

            STTResponse response = new STTResponse(
                    text,
                    "en",
                    0.9
            );

            return ResponseEntity.ok(response);

        } catch (IOException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
