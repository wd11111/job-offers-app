package com.joboffers.offer.containertest;

import com.joboffers.JobOffersApplication;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.OfferRepository;
import com.joboffers.offer.OfferService;
import com.joboffers.offer.Samples;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.joboffers.offer.containertest.Config.*;
import static com.joboffers.offer.containertest.OfferListAssert.*;

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
    void should_add_list_of_two_offers() {
        List<OfferDto> offersToAdd = List.of(sampleOfferDto4(), sampleOfferDto5());
        assertThatOffersDoesNotExistInDatabase(offersToAdd, offerRepository);

        List<OfferDto> savedOffers = offerService.saveAllOffersAfterFiltered(offersToAdd);

        assertThatOffersWereAdded(savedOffers, offerRepository);
    }

    @Import(JobOffersApplication.class)
    static class TestConfig {
    }
}
