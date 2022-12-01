package com.joboffers.infrastructure.offer.client;

import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.Samples;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class OfferHttpClientTest implements SampleRestTemplateExchangeResponse, SampleOfferResponse, Samples {

    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    RemoteOfferClient offerHttpClient = new OfferHttpClient(restTemplate, "url",  5057, "path");

    @Test
    void should_return_one_element_list_of_offers() {
        when(exchange(restTemplate)).thenReturn(responseWithOneOffer());

        List<OfferDto> offers = offerHttpClient.getOffers();

        assertThat(offers).hasSize(1);
    }

    @Test
    void should_return_empty_list_of_offers() {
        when(exchange(restTemplate)).thenThrow(RestClientException.class);

        List<OfferDto> offers = offerHttpClient.getOffers();

        assertThat(offers).isEmpty();
    }

    @Test
    void should_return_two_offers() {
        when(exchange(restTemplate)).thenReturn(responseWithOffers(sampleOfferDto1(), sampleOfferDto2()));

        List<OfferDto> offers = offerHttpClient.getOffers();

        assertThat(offers).hasSize(2);
    }


}
