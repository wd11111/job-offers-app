package com.joboffers.infrastructure.offer.client;

import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.model.OfferDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class OfferHttpClient implements RemoteOfferClient {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<OfferDto> getOffers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(httpHeaders);
        String url = uri + ":" + port + "/offers";

        try {
            ResponseEntity<List<OfferDto>> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                    new ParameterizedTypeReference<List<OfferDto>>() {
                    });
            final List<OfferDto> body = response.getBody();
            return (body != null) ? body : Collections.emptyList();
        } catch (ResourceAccessException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
