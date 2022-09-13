package com.joboffers.offer;

import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import org.mockito.ArgumentMatchers;

import java.util.List;

interface SampleOffers {

    default OfferDto sampleDtoOffer1() {
        return new OfferDto("Junior DevOps Engineer", "CDQ Poland", "8k - 14k PLN", "https://nofluffjobs.com/pl/job/junior-devops-engineer-cdq-poland-wroclaw-gnymtxqd");
    }

    default OfferDto sampleDtoOffer2() {
        return new OfferDto("Software Engineer - Mobile (m/f/d)", "Cybersource", "4k - 8k PLN", "https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn");
    }

    default List<OfferDto> sampleListOfOfferDto() {
        return List.of(sampleDtoOffer1(), sampleDtoOffer1());
    }

    default String getByIdUrl() {
        return "/offers/1";
    }

    default String anyId() {
        return ArgumentMatchers.anyString();
    }

    default Offer sampleOffer1() {
        return Offer.builder()
                .title("Junior DevOps Engineer")
                .company("CDQ Poland")
                .salary("8k - 14k PLN")
                .url("https://nofluffjobs.com/pl/job/junior-devops-engineer-cdq-poland-wroclaw-gnymtxqd")
                .build();
    }

    default Offer sampleOffer2() {
        return Offer.builder()
                .title("Software Engineer - Mobile (m/f/d)")
                .company("Cybersource")
                .salary("4k - 8k PLN")
                .url("https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn")
                .build();
    }
}
