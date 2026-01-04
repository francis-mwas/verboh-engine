package com.coop.demo.coopVerboh.service;

import com.coop.demo.coopVerboh.dtos.STTResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
@RequiredArgsConstructor
public class STTService {

    @Value("${stt.endpoint:http://localhost:8001/api/v1/stt/transcribe}")
    private String sttEndpoint;

    // Add your security key from application.properties
    @Value("${stt.api.key:your_actual_key_here}")
    private String serviceKey;

    private final RestTemplate restTemplate;

    public String transcribeAudio(byte[] audioBytes) {
        if (audioBytes == null || audioBytes.length == 0) {
            log.warn("Empty audio bytes received for transcription");
            return "";
        }

        try {
            // 1. Set Headers (Multipart + Auth)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("X-Service-Key", serviceKey); // Ensure key matches your FastAPI security util

            // 2. Prepare the File Part
            // We wrap the resource to provide a filename and content type
            LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            HttpEntity<ByteArrayResource> filePart = createHttpEntityForFile(audioBytes, "audio.wav");
            body.add("file", filePart);

            // 3. Send Request
            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            log.info("Sending STT request to: {}", sttEndpoint);

            ResponseEntity<STTResponse> response = restTemplate.exchange(
                    sttEndpoint,
                    HttpMethod.POST,
                    requestEntity,
                    STTResponse.class
            );

            return (response.getBody() != null) ? response.getBody().getText() : "";

        } catch (Exception ex) {
            log.error("STT Transcription failed: {}", ex.getMessage());
            return "";
        }
    }

    /**
     * Helper to wrap bytes into a format RestTemplate/FastAPI understands
     */
    private HttpEntity<ByteArrayResource> createHttpEntityForFile(byte[] audioBytes, String filename) {
        ByteArrayResource resource = new ByteArrayResource(audioBytes) {
            @Override
            public String getFilename() {
                return filename;
            }
        };

        HttpHeaders partHeaders = new HttpHeaders();
        partHeaders.setContentType(MediaType.parseMediaType("audio/wav"));
        return new HttpEntity<>(resource, partHeaders);
    }
}