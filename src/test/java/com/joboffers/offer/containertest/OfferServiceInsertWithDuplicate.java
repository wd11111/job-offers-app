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

import static com.joboffers.offer.containertest.Config.DB_PORT;
import static com.joboffers.offer.containertest.Config.DOCKER_IMAGE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(classes = OfferServiceInsertWithDuplicate.TestConfig.class)
@ActiveProfiles("container")
@Testcontainers
public class OfferServiceInsertWithDuplicate implements Samples {

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
    void should_add_only_one_offer_after_list_of_offers_is_filtered_of_duplicates() {
        List<OfferDto> offersToAddWithOneDuplicate = List.of(sampleOfferDto1(), sampleOfferDto6());
        String urlOfNonDuplicateOffer = sampleOfferDto6().getOfferUrl();
        String urlOfDuplicateOffer = sampleOfferDto1().getOfferUrl();
        then(offerRepository.existsByOfferUrl(urlOfNonDuplicateOffer)).isFalse();
        then(offerRepository.existsByOfferUrl(urlOfDuplicateOffer)).isTrue();

        List<OfferDto> savedOffers = offerService.saveAllOffersAfterFiltered(offersToAddWithOneDuplicate);

        assertThat(savedOffers.size()).isEqualTo(1);
        assertThat(offerRepository.existsByOfferUrl(urlOfNonDuplicateOffer)).isTrue();
    }

    @Import(JobOffersApplication.class)
    static class TestConfig {
    }
}
