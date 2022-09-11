package com.joboffers.offer;

import com.joboffers.model.OfferDto;
import com.joboffers.model.OfferResponse;

public class OfferMapper {

    public static OfferDto mapToOfferDto(OfferResponse offerResponse) {
        return OfferDto.builder()
                .title(offerResponse.getTitle())
                .offerUrl(offerResponse.getOfferUrl())
                .salary(offerResponse.getSalary())
                .company(offerResponse.getCompany())
                .build();
    }

}
