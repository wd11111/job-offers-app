package com.joboffers.config;

import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.infrastructure.offer.client.OfferHttpClient;
import com.joboffers.infrastructure.offer.error.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class OfferClientConfig {

    @Bean
    RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    RestTemplate restTemplate(@Value("${offer.http.client.config.connectionTimeout:1000}") long connectionTimeout,
                                        @Value("${offer.http.client.config.readTimeout:1000}") long readTimeout,
                                        RestTemplateResponseErrorHandler errorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(errorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean
    RemoteOfferClient offerClient(RestTemplate restTemplate,
                                            @Value("${offer.http.client.config.uri:http://example.com}") String uri,
                                            @Value("${offer.http.client.config.port:5057}") int port) {
        return new OfferHttpClient(restTemplate, uri, port);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
