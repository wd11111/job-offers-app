package com.joboffers.config;

import com.github.cloudyrock.spring.v5.EnableMongock;
import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.infrastructure.offer.client.OfferHttpClient;
import com.joboffers.infrastructure.offer.error.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Duration;

@Configuration
@EnableMongock
@EnableCaching
public class Config {

    @Bean
    RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    RestTemplate restTemplate(@Value("${offer.http.client.config.connectionTimeout}") long connectionTimeout,
                                        @Value("${offer.http.client.config.readTimeout}") long readTimeout,
                                        RestTemplateResponseErrorHandler errorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(errorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean
    RemoteOfferClient offerClient(RestTemplate restTemplate,
                                            @Value("${offer.http.client.config.uri}") String uri,
                                            @Value("${offer.http.client.config.port}") int port) {
        return new OfferHttpClient(restTemplate, uri, port);
    }
}
