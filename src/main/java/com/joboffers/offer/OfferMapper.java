package com.joboffers.offer;

import com.joboffers.model.OfferDto;
import com.joboffers.model.Offer;

public class OfferMapper {

    public static OfferDto mapToOfferDto(Offer offer) {
        return OfferDto.builder()
                .title(offer.getTitle())
                .url(offer.getUrl())
                .salary(offer.getSalary())
                .company(offer.getCompany())
                .build();
    }

}
