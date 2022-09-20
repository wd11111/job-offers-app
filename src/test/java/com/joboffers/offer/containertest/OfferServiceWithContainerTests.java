package com.joboffers.offer.containertest;

import com.joboffers.JobOffersApplication;
import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.OfferRepository;
import com.joboffers.offer.OfferService;
import com.joboffers.offer.exceptions.OfferDuplicateException;
import com.joboffers.offer.exceptions.OfferNotFoundException;
import com.joboffers.offer.service.Samples;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(classes = JobOffersApplication.class)
@ActiveProfiles("container")
@Testcontainers
class OfferServiceWithContainerTests implements Samples {

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    OfferService offerService;

    private static final String MONGO_VERSION = "4.4.4";

    @Container
    private static final MongoDBContainer DB_CONTAINER = new MongoDBContainer("mongo:" + MONGO_VERSION);

    static {
        DB_CONTAINER.start();
        System.setProperty("DB_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @Test
    void should_return_list_of_all_offers() {
        then(offerRepository.findAll()).containsAll(List.of(sampleOffer1(), sampleOffer2()));

        List<OfferDto> allOffers = offerService.findAll();

        assertThat(allOffers).containsAll(List.of(sampleOfferDto1(), sampleOfferDto2()));
    }

    @Test
    void should_return_correct_offer() {
        then(offerRepository.findById("63223dcb1a420777c05ffd79")).isPresent();

        OfferDto offerById = offerService.findById("63223dcb1a420777c05ffd79");

        assertThat(offerById).isEqualTo(sampleOfferDto1());
    }

    @Test
    void should_throw_offer_not_found_exception() {
        then(offerRepository.findById("wrong_id")).isNotPresent();

        assertThatThrownBy(() -> {
            offerService.findById("wrong_id");
        }).isInstanceOf(OfferNotFoundException.class)
                .hasMessageContaining(String.format("Offer with id %s not found", "wrong_id"));
    }

    @Test
    void should_correctly_add_offer_to_db() throws UnsupportedEncodingException {
        OfferDto offerToAdd = sampleOfferDto3();
        then(offerRepository.existsByOfferUrl(offerToAdd.getOfferUrl())).isFalse();

        OfferDto addedOffer = offerService.addOffer(offerToAdd);
        Offer actualOffer = offerRepository.findByOfferUrl(addedOffer.getOfferUrl());

        OfferBodyAssert.then(actualOffer).isTheSameAs(addedOffer);
    }

    @Test
    void should_not_add_offer_when_url_is_a_duplicate() {
        OfferDto offerToInsert = sampleOfferDto1();
        then(offerRepository.existsByOfferUrl(offerToInsert.getOfferUrl())).isTrue();

        assertThatThrownBy(() -> {
            offerService.addOffer(offerToInsert);
        }).isInstanceOf(OfferDuplicateException.class)
                .hasMessageContaining("Offer with this url already exists");
    }
}
