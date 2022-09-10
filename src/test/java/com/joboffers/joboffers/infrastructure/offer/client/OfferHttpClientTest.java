package com.joboffers.joboffers.infrastructure.offer.client;

import com.joboffers.joboffers.infrastructure.offer.dto.OfferDto;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class OfferHttpClientTest {

    @Test
    void should_return_one_element_list_of_offers() {
        // given
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        String uri = "test";
        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<OfferDto>>>any()))
                .thenReturn(new ResponseEntity<>(Collections.singletonList(new OfferDto()), HttpStatus.ACCEPTED));

        // when
        OfferHttpClient offerHttpClient = new OfferHttpClient(restTemplate, uri);
        List<OfferDto> offers = offerHttpClient.getOffers();
        //then
        assertThat(offers.size()).isEqualTo(1);
    }

}