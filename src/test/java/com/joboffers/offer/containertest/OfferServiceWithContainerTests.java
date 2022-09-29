package com.joboffers.offer.containertest;

import com.joboffers.JobOffersApplication;
import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.OfferRepository;
import com.joboffers.offer.OfferService;
import com.joboffers.offer.exception.OfferDuplicateException;
import com.joboffers.offer.exception.OfferNotFoundException;
import com.joboffers.offer.service.Samples;
import com.joboffers.security.repository.UserRepository;
import com.joboffers.security.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private static final String MONGO_VERSION = "4.4.4";

    @Container
    private static final MongoDBContainer DB_CONTAINER = new MongoDBContainer("mongo:" + MONGO_VERSION);

    static {
        DB_CONTAINER.start();
        System.setProperty("DB_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @Test
    void should_return_list_of_all_offers(@Autowired OfferService offerService, @Autowired OfferRepository offerRepository) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title").ascending());
        List<Offer> offersFromRepo = List.of(sampleOffer1());
        then(offerRepository.findAll(pageable)).containsExactlyInAnyOrderElementsOf(new PageImpl<>(offersFromRepo));
        // System.out.println(offerRepository.findAll(pageable));;

        List<OfferDto> allOffers = offerService.findAll(1, "name", "asc");

        assertThat(allOffers).containsAll(List.of(sampleOfferDto1()));
    }

    @Test
    void should_return_correct_offer(@Autowired OfferService offerService, @Autowired OfferRepository offerRepository) {
        then(offerRepository.findById("63223dcb1a420777c05ffd79")).isPresent();

        OfferDto offerById = offerService.findById("63223dcb1a420777c05ffd79");

        assertThat(offerById).isEqualTo(sampleOfferDto1());
    }

    @Test
    void should_throw_offer_not_found_exception(@Autowired OfferService offerService, @Autowired OfferRepository offerRepository) {
        then(offerRepository.findById("wrong_id")).isNotPresent();

        assertThatThrownBy(() -> {
            offerService.findById("wrong_id");
        }).isInstanceOf(OfferNotFoundException.class)
                .hasMessageContaining(String.format("Offer with id %s not found", "wrong_id"));
    }

    @Test
    void should_correctly_add_offer_to_db(@Autowired OfferService offerService, @Autowired OfferRepository offerRepository) throws UnsupportedEncodingException {
        OfferDto offerToAdd = sampleOfferDto3();
        String urlOfOffer = offerToAdd.getOfferUrl();
        then(offerRepository.existsByOfferUrl(urlOfOffer)).isFalse();

        OfferDto addedOffer = offerService.addOffer(offerToAdd);
        Offer actualOffer = offerRepository.findByOfferUrl(addedOffer.getOfferUrl());

        OfferBodyAssert.then(actualOffer).isTheSameAs(addedOffer);
    }

    @Test
    void should_not_add_offer_when_url_is_a_duplicate(@Autowired OfferService offerService, @Autowired OfferRepository offerRepository) {
        OfferDto offerToAdd = sampleOfferDto1();
        String urlOfOffer = offerToAdd.getOfferUrl();
        then(offerRepository.existsByOfferUrl(urlOfOffer)).isTrue();

        assertThatThrownBy(() -> {
            offerService.addOffer(offerToAdd);
        }).isInstanceOf(OfferDuplicateException.class)
                .hasMessageContaining("Offer with this url already exists");
    }

    @Test
    void should_add_list_of_two_offers(@Autowired OfferService offerService, @Autowired OfferRepository offerRepository) {
        List<OfferDto> offersToAdd = List.of(sampleOfferDto4(), sampleOfferDto5());
        OfferListAssert.assertThatOffersDoesNotExistInDb(offersToAdd, offerRepository);

        List<OfferDto> savedOffers = offerService.saveAllAfterFiltered(offersToAdd);

        OfferListAssert.assertThatOffersWereAdded(savedOffers, offerRepository);
    }

    @Test
    void should_add_only_one_offer_after_list_of_offers_is_filtered_of_duplicates(@Autowired OfferService offerService, @Autowired OfferRepository offerRepository) {
        List<OfferDto> offersToAddWithOneDuplicate = List.of(sampleOfferDto1(), sampleOfferDto6());
        String urlOfNonDuplicateOffer = sampleOfferDto6().getOfferUrl();
        String urlOfDuplicateOffer = sampleOfferDto1().getOfferUrl();
        then(offerRepository.existsByOfferUrl(urlOfNonDuplicateOffer)).isFalse();
        then(offerRepository.existsByOfferUrl(urlOfDuplicateOffer)).isTrue();

        List<OfferDto> savedOffers = offerService.saveAllAfterFiltered(offersToAddWithOneDuplicate);

        assertThat(savedOffers.size()).isEqualTo(1);
        assertThat(offerRepository.existsByOfferUrl(urlOfNonDuplicateOffer)).isTrue();
    }
}
