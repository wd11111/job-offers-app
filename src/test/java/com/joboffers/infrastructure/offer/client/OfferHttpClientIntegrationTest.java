package com.joboffers.infrastructure.offer.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.joboffers.config.OfferHttpClientTestOfferClientConfig;
import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.offer.service.Samples;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SocketUtils;
import wiremock.org.apache.http.HttpStatus;

import java.util.Arrays;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.BDDAssertions.then;

class OfferHttpClientIntegrationTest implements Samples {

    int port = SocketUtils.findAvailableTcpPort();
    WireMockServer wireMockServer;

    RemoteOfferClient remoteOfferClient = new OfferHttpClientTestOfferClientConfig().remoteOfferTestClient("http://localhost", port, 1000, 1000);

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(options().port(port));
        wireMockServer.start();
        WireMock.configureFor(port);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void should_return_two_job_offers() {
        WireMock.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithTwoOffersJson())));

        then(remoteOfferClient.getOffers())
                .containsExactlyInAnyOrderElementsOf(Arrays.asList(sampleOfferDto1(), sampleOfferDto2()));
    }

    private String bodyWithTwoOffersJson() {
        return "[{\n" +
                "    \"title\": \"Remote Junior Java Developer\",\n" +
                "    \"company\": \"Tutlo sp zoo\",\n" +
                "    \"salary\": \"8 000 - 12 000 PLN\",\n" +
                "    \"offerUrl\": \"https://nofluffjobs.com/pl/job/remote-junior-java-developer-tutlo-yywmpzo0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"Junior Salesforce/Fullstack Developer\",\n" +
                "    \"company\": \"Youdigital Sp. z o.o.\",\n" +
                "    \"salary\": \"4 500 - 8 500 PLN\",\n" +
                "    \"offerUrl\": \"https://nofluffjobs.com/pl/job/junior-salesforce-fullstack-developer-youdigital-lodz-jzt8qjvv\"\n" +
                "  }]";
    }
}

