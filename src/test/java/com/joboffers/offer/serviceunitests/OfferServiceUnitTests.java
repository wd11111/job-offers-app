package com.joboffers.offer.serviceunitests;

import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.OfferRepository;
import com.joboffers.offer.OfferService;
import com.joboffers.offer.exceptions.OfferNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OfferServiceUnitTests implements Samples {

    OfferRepository offerRepository = mock(OfferRepository.class);
    OfferService offerService = new OfferService(offerRepository);

    @Test
    void should_return_list_of_three_dto_offers() {
        List<OfferDto> expectedOffers = List.of(sampleOfferDto1(), sampleOfferDto2(), sampleOfferDto3());
        List<Offer> offersFromRepo = List.of(sampleOffer1(), sampleOffer2(), sampleOffer3());
        when(offerRepository.findAll()).thenReturn(offersFromRepo);

        List<OfferDto> offerList = offerService.getOfferList();

        assertThat(offerList).containsAll(expectedOffers);
    }

    @ParameterizedTest(name = "offer dto to return {0}, offer from repo {1}, given id {2}")
    @ArgumentsSource(ProvideArgumentsOfOffers.class)
    void should_return_offer_dto_by_id(OfferDto expectedOffer, Offer offerFromRepo, String id) {
        when(offerRepository.findById(id)).thenReturn(Optional.of(offerFromRepo));

        OfferDto offer = offerService.getOfferById(id);

        assertThat(offer).isEqualTo(expectedOffer);
    }

    @Test
    void should_throw_exception_when_offer_does_not_exist() {
        String sampleId = "63223dcb1a420777c05ffd7c";
        when(offerRepository.findById(sampleId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            offerService.getOfferById(sampleId);
        }).isInstanceOf(OfferNotFoundException.class)
                .hasMessageContaining(String.format("Offer with id %s not found", sampleId));
    }


}