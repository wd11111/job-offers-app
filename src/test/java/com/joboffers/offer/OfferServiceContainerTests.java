package com.joboffers.offer;

import com.joboffers.JobOffersApplication;
import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(classes = JobOffersApplication.class)
@TestPropertySource(properties = "spring.cache.type=none")
@ActiveProfiles("container")
public class OfferServiceContainerTests implements SampleOffers {

    @Container
    private final static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.4");

    static {
        mongoDBContainer.start();
        System.setProperty("DB_PORT", String.valueOf(mongoDBContainer.getFirstMappedPort()));
    }

    @Autowired
    OfferService offerService;

    @Test
    void should_return_all_offers() {
        List<OfferDto> expectedOffers = List.of(sampleDtoOffer1(), sampleDtoOffer2());

        List<OfferDto> offersFromDb = offerService.getOfferList();

        assertThat(offersFromDb).containsAll(expectedOffers);
    }


    @Test
    void should_return_one_offer_by_id() {
        OfferDto expectedOffer = sampleDtoOffer1();
        String offerId = "6321d387c9db7f57affe5049";

        OfferDto offerFromDb = offerService.getOfferById(offerId);


        assertThat(expectedOffer).isEqualTo(offerFromDb);
    }

    @Test
    void should_save_list_of_two_offers_properly_in_database() {
        List<Offer> offersToSave = List.of(sampleOffer1(), sampleOffer2());
        int collectionSizeBeforeSave = offerService.getOfferList().size();

        List<Offer> savedOffers = offerService.saveAll(offersToSave);
        int collectionSizeAfterSave = offerService.getOfferList().size();

        assertThat(collectionSizeBeforeSave).isLessThan(collectionSizeAfterSave);
    }


}
