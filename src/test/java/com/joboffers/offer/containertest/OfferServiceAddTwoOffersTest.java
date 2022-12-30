package com.joboffers.offer.containertest;

import com.joboffers.JobOffersApplication;
import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.OfferRepository;
import com.joboffers.offer.OfferService;
import com.joboffers.offer.Samples;
import com.joboffers.scheduling.HttpOfferScheduler;
import com.joboffers.security.handler.FailureHandler;
import com.joboffers.security.handler.SuccessHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.joboffers.offer.containertest.Config.DB_PORT;
import static com.joboffers.offer.containertest.Config.DOCKER_IMAGE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(classes = OfferServiceAddTwoOffersTest.TestConfig.class)
@ActiveProfiles("container")
@Testcontainers
public class OfferServiceAddTwoOffersTest implements Samples {

    @Autowired
    OfferService offerService;

    @Autowired
    OfferRepository offerRepository;

    @Container
    private static final MongoDBContainer DB_CONTAINER = new MongoDBContainer(DOCKER_IMAGE_NAME);

    static {
        DB_CONTAINER.start();
        System.setProperty(DB_PORT, String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @Test
    void should_add_two_offers() {
        List<OfferDto> offersToAdd = List.of(sampleOfferDto4(), sampleOfferDto5());
        then(offerRepository.findAll().size()).isEqualTo(2);

        offerService.saveListOfOffers(offersToAdd);

        assertThat(offerRepository.findAll().size()).isEqualTo(4);
    }

    @Import(JobOffersApplication.class)
    static class TestConfig {

        @MockBean
        HttpOfferScheduler httpOfferScheduler;
        @MockBean
        SuccessHandler successHandler;
        @MockBean
        FailureHandler failureHandler;
        @MockBean
        RemoteOfferClient remoteOfferClient;
    }
}
