package com.joboffers.offer.containertest;

import com.joboffers.JobOffersApplication;
import com.joboffers.model.Offer;
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

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(classes = OfferServiceInsertContainerTest.TestConfig.class)
@ActiveProfiles("container")
@Testcontainers
public class OfferServiceInsertContainerTest implements Samples {

    private static final String MONGO_VERSION = "4.4.4";

    @Autowired
    OfferService offerService;

    @Autowired
    OfferRepository offerRepository;

    @Container
    private static final MongoDBContainer DB_CONTAINER = new MongoDBContainer("mongo:" + MONGO_VERSION);

    static {
        DB_CONTAINER.start();
        System.setProperty("DB_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @Test
    void should_correctly_add_offer_to_db() throws UnsupportedEncodingException {
        OfferDto offerToAdd = sampleOfferDto3();
        String urlOfOffer = offerToAdd.getOfferUrl();
        then(offerRepository.existsByOfferUrl(urlOfOffer)).isFalse();

        OfferDto addedOffer = offerService.addOffer(offerToAdd);
        Offer actualOffer = offerRepository.findByOfferUrl(addedOffer.getOfferUrl());

        OfferBodyAssert.then(actualOffer).isTheSameAs(addedOffer);
    }

    @Import(JobOffersApplication.class)
    static class TestConfig {
    }
}
