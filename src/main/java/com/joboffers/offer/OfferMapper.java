package com.joboffers.offer;

import com.joboffers.model.OfferDto;
import com.joboffers.model.Offer;

public class OfferMapper {

    public static OfferDto mapToOfferDto(Offer offer) {
        return OfferDto.builder()
                .title(offer.getTitle())
                .offerUrl(offer.getOfferUrl())
                .salary(offer.getSalary())
                .company(offer.getCompany())
                .build();
    }

    public static Offer mapToOffer(OfferDto offer) {
        return Offer.builder()
                .title(offer.getTitle())
                .offerUrl(offer.getOfferUrl())
                .salary(offer.getSalary())
                .company(offer.getCompany())
                .build();
    }

}
