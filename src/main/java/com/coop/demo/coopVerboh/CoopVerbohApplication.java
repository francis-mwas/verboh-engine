package com.coop.demo.coopVerboh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.coop.demo.coopVerboh")
public class CoopVerbohApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoopVerbohApplication.class, args);
    }

    // RestTemplate bean for calling external STT/TTS services
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
