package com.payment.billing.infra.toss.config;

import java.time.Duration;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class TossConfig {

    @Value("${toss.secret-key}")
    private String secretKey;

    @Value("${toss.payment.url}")
    private String baseUrl;

    @Bean
    public RestClient tossRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Basic " + encodeSecretKey())
                .defaultHeader("Content-Type", "application/json")
                .requestFactory(requestFactory())
                .build();
    }

    private ClientHttpRequestFactory requestFactory() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withReadTimeout(Duration.ofSeconds(30))
                .withConnectTimeout(Duration.ofSeconds(6));
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(settings);
        return requestFactory;
    }

    private String encodeSecretKey() {
        return Base64.getEncoder().encodeToString((secretKey + ":").getBytes());
    }
}
