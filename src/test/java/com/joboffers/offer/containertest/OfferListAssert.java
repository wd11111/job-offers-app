package com.joboffers.offer.containertest;

import com.joboffers.model.OfferDto;
import com.joboffers.offer.OfferRepository;
import lombok.AllArgsConstructor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor
public class OfferListAssert {

    static void assertThatOffersWereAdded(List<OfferDto> offers, OfferRepository offerRepository) {
        offers.forEach(offer -> assertThat(offerRepository.existsByOfferUrl(offer.getOfferUrl())).isTrue());
    }

    static void assertThatOffersDoesNotExistInDatabase(List<OfferDto> offers, OfferRepository offerRepository) {
        offers.forEach(offer -> assertThat(offerRepository.existsByOfferUrl(offer.getOfferUrl())).isFalse());
    }

}
