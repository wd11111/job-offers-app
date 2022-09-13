package com.joboffers.infrastructure.offer.client;

import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.model.OfferDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class OfferHttpClientTest implements SampleRestTemplateExchangeResponse, SampleOfferResponse, SampleOfferDto {

    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    RemoteOfferClient offerHttpClient = new OfferHttpClient(restTemplate, "url", 5057);

    @Test
    void should_return_one_element_list_of_offers() {
        // given
        when(getExchange(restTemplate))
                .thenReturn(responseWithOneOffer());

        // when
        List<OfferDto> offers = offerHttpClient.getOffers();

        //then
        assertThat(offers.size()).isEqualTo(1);
    }

    @Test
    void should_return_empty_list_of_offers() {
        // given
        when(getExchange(restTemplate))
                .thenReturn(responseWithNoOffers());

        // when
        List<OfferDto> offers = offerHttpClient.getOffers();

        // then
        assertThat(offers).isEmpty();
    }

    @Test
    void should_return_two_offers() {
        // given
        when(getExchange(restTemplate))
                .thenReturn(responseWithOffers(emptyOffer(), emptyOffer()));

        // when
        List<OfferDto> offers = offerHttpClient.getOffers();

        //then
        assertThat(offers.size()).isEqualTo(2);
    }


}
