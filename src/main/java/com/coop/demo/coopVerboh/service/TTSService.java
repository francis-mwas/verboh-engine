package com.coop.demo.coopVerboh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TTSService {

    @Value("${tts.endpoint:http://localhost:8002/api/v1/tts/synthesize}")
    private String ttsEndpoint;

    @Value("${tts.api.key:YOUR_DEFAULT_KEY}")
    private String serviceKey;

    private final RestTemplate restTemplate;

    public byte[] synthesizeSpeech(String text) {
        if (text == null || text.isBlank()) {
            return new byte[0];
        }

        try {
            // 1. Set Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            // This is the missing piece causing your 422 error
            headers.set("x-service-key", serviceKey);

            // 2. Prepare Form Data (Spring handles "text=" + text encoding better this way)
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("text", text);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

            log.info("Requesting TTS for text: '{}' at {}", text, ttsEndpoint);

            // 3. Execute request
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    ttsEndpoint,
                    HttpMethod.POST,
                    requestEntity,
                    byte[].class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("TTS Success: Received {} bytes", response.getBody() != null ? response.getBody().length : 0);
                return response.getBody();
            }

            return new byte[0];

        } catch (Exception e) {
            log.error("TTS synthesis failed for text: {}. Error: {}", text, e.getMessage());
            return new byte[0];
        }
    }
}