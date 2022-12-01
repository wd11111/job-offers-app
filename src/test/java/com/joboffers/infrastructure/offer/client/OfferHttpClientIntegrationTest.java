package com.joboffers.infrastructure.offer.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.joboffers.config.OfferHttpClientTestConfig;
import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.Samples;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SocketUtils;
import wiremock.org.apache.http.HttpStatus;

import java.util.List;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

class OfferHttpClientIntegrationTest implements Samples {

    private static String PATH_VARIABLE = "/offers";

    int port = SocketUtils.findAvailableTcpPort();

    private final ObjectMapper objectMapper = new ObjectMapper();
    WireMockServer wireMockServer;

    RemoteOfferClient remoteOfferClient = new OfferHttpClientTestConfig().remoteOfferTestClient("http://localhost", port, PATH_VARIABLE, 1000, 1000);

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(options().port(port));
        wireMockServer.start();
        WireMock.configureFor(port);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void should_return_two_job_offers() throws JsonProcessingException {
        List<OfferDto> listOfOffers = List.of(sampleOfferDto1(), sampleOfferDto2());
        String jsonBodyWithTwoOffers = objectMapper.writeValueAsString(listOfOffers);

        WireMock.stubFor(WireMock.get(PATH_VARIABLE)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonBodyWithTwoOffers)));
        List<OfferDto> result = remoteOfferClient.getOffers();

        assertThat(result).containsExactlyInAnyOrderElementsOf(listOfOffers);
    }

    @Test
    void should_return_empty_collection() throws JsonProcessingException {
        String nullResponse = objectMapper.writeValueAsString(null);

        WireMock.stubFor(WireMock.get(PATH_VARIABLE)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withBody(nullResponse)));
        List<OfferDto> result = remoteOfferClient.getOffers();

        assertThat(result).isEmpty();
    }

}

