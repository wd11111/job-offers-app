package com.joboffers.offer;

import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<Offer> mapToListOfOffers(List<OfferDto> offers) {
        return offers.stream()
                .map(OfferMapper::mapToOffer)
                .collect(Collectors.toList());
    }

}
