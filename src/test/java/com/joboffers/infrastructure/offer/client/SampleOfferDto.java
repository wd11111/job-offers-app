package com.joboffers.infrastructure.offer.client;

import com.joboffers.model.OfferDto;

public interface SampleOfferDto {

    default OfferDto emptyOffer() {
        return new OfferDto();
    }

    default OfferDto offerWithParameters (String title, String company, String salary, String offerUrl) {
        return OfferDto.builder()
                .title(title)
                .company(company)
                .salary(salary)
                .offerUrl(offerUrl)
                .build();
    }
}
