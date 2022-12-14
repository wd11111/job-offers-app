package com.joboffers.offer.containertest;

import com.joboffers.JobOffersApplication;
import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.OfferRepository;
import com.joboffers.offer.OfferService;
import com.joboffers.offer.Samples;
import com.joboffers.offer.exception.OfferDuplicateException;
import com.joboffers.offer.exception.OfferNotFoundException;
import com.joboffers.scheduling.HttpOfferScheduler;
import com.joboffers.security.handler.FailureHandler;
import com.joboffers.security.handler.SuccessHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.joboffers.offer.containertest.Config.DB_PORT;
import static com.joboffers.offer.containertest.Config.DOCKER_IMAGE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(classes = OfferServiceWithContainerTests.TestConfig.class)
@ActiveProfiles("container")
@Testcontainers
class OfferServiceWithContainerTests implements Samples {

    private static final String WRONG_ID = "wrong_id";

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
    void should_return_list_of_all_offers() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title").ascending());
        List<Offer> offersFromRepo = List.of(sampleOffer1(), sampleOffer2());
        List<OfferDto> expectedOffers = List.of(sampleOfferDto1(), sampleOfferDto2());
        then(offerRepository.findAll(pageable)).containsExactlyInAnyOrderElementsOf(new PageImpl<>(offersFromRepo));

        List<OfferDto> allOffers = offerService.findAll(1, "name", "asc");

        assertThat(allOffers).containsAll(expectedOffers);
    }

    @Test
    void should_return_correct_offer() {
        then(offerRepository.findById("63223dcb1a420777c05ffd79")).isPresent();

        OfferDto offerById = offerService.findById("63223dcb1a420777c05ffd79");

        assertThat(offerById).isEqualTo(sampleOfferDto1());
    }

    @Test
    void should_throw_offer_not_found_exception() {
        then(offerRepository.findById(WRONG_ID)).isNotPresent();

        assertThatThrownBy(() -> offerService.findById(WRONG_ID)).isInstanceOf(OfferNotFoundException.class)
                .hasMessageContaining(String.format("Offer with id %s not found", "wrong_id"));
    }

    @Test
    void should_throw_offer_duplicate_exception() {
        OfferDto offerToAdd = sampleOfferDto1();
        String urlOfOffer = offerToAdd.getOfferUrl();
        then(offerRepository.existsByOfferUrl(urlOfOffer)).isTrue();

        assertThatThrownBy(() -> offerService.saveOffer(offerToAdd)).isInstanceOf(OfferDuplicateException.class)
                .hasMessageContaining("Offer with this url already exists");
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
