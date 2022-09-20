package com.joboffers.offer.containertest;

import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import lombok.AllArgsConstructor;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor
public class OfferBodyAssert {

    private final Offer offer;

    static OfferBodyAssert then(Offer offer) {
        return new OfferBodyAssert(offer);
    }

    OfferBodyAssert isTheSameAs(OfferDto offerDto) throws UnsupportedEncodingException {
        assertThat(offer.getTitle()).isEqualTo(offerDto.getTitle());
        assertThat(offer.getCompany()).isEqualTo(offerDto.getCompany());
        assertThat(offer.getSalary()).isEqualTo(offerDto.getSalary());
        assertThat(offer.getOfferUrl()).isEqualTo(offerDto.getOfferUrl());
        return this;
    }
}
