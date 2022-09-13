package com.joboffers.config;

import com.joboffers.infrastructure.RemoteOfferClient;
import org.springframework.web.client.RestTemplate;

public class OfferHttpClientTestConfig extends Config {

    public RemoteOfferClient remoteOfferTestClient(String uri, int port, int connectionTimeout, int readTimeout) {
        final RestTemplate restTemplate = restTemplate(connectionTimeout, readTimeout, restTemplateResponseErrorHandler());
        return offerClient(restTemplate, uri, port);
    }

}
