package com.joboffers.offer.serviceunitests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public interface Samples {

    default Offer sampleOffer1() {
        return new Offer("63223dcb1a420777c05ffd79", "Remote Junior Java Developer",
                "Tutlo sp zoo", "8 000 - 12 000 PLN",
                "https://nofluffjobs.com/pl/job/remote-junior-java-developer-tutlo-yywmpzo0");
    }

    default Offer sampleOffer2() {
        return new Offer("63223dcb1a420777c05ffd7a", "Junior Salesforce/Fullstack Developer",
                "Youdigital Sp. z o.o.", "4 500 - 8 500 PLN",
                "https://nofluffjobs.com/pl/job/junior-salesforce-fullstack-developer-youdigital-lodz-jzt8qjvv");
    }

    default Offer sampleOffer3() {
        return new Offer("63223dcb1a420777c05ffd7c", "Junior Framework Developer",
                "Blackbelt Holding Zrt", "4 689 - 7 034 PLN",
                "https://nofluffjobs.com/pl/job/junior-framework-developer-blackbelt-holding-zrt-budapest-9wbjcnzy");
    }

    default Offer sampleOfferWithOutId1() {
        return Offer.builder()
                .title("Remote Junior Java Developer")
                .company("Tutlo sp zoo")
                .salary("8 000 - 12 000 PLN")
                .offerUrl("https://nofluffjobs.com/pl/job/remote-junior-java-developer-tutlo-yywmpzo0")
                .build();
    }

    default Offer sampleOfferWithOutId2() {
        return Offer.builder()
                .title("Junior Salesforce/Fullstack Developer")
                .company("Youdigital Sp. z o.o.")
                .salary("4 500 - 8 500 PLN")
                .offerUrl("https://nofluffjobs.com/pl/job/junior-salesforce-fullstack-developer-youdigital-lodz-jzt8qjvv")
                .build();
    }

    default Offer sampleOfferWithOutId3() {
        return Offer.builder()
                .title("Junior Framework Developer")
                .company("Blackbelt Holding Zrt")
                .salary("4 689 - 7 034 PLN")
                .offerUrl("https://nofluffjobs.com/pl/job/junior-framework-developer-blackbelt-holding-zrt-budapest-9wbjcnzy")
                .build();
    }

    default OfferDto sampleOfferDto1() {
        return new OfferDto("Remote Junior Java Developer",
                "Tutlo sp zoo", "8 000 - 12 000 PLN",
                "https://nofluffjobs.com/pl/job/remote-junior-java-developer-tutlo-yywmpzo0");
    }

    default OfferDto sampleOfferDto2() {
        return new OfferDto("Junior Salesforce/Fullstack Developer",
                "Youdigital Sp. z o.o.", "4 500 - 8 500 PLN",
                "https://nofluffjobs.com/pl/job/junior-salesforce-fullstack-developer-youdigital-lodz-jzt8qjvv");
    }

    default OfferDto sampleOfferDto3() {
        return new OfferDto("Junior Framework Developer",
                "Blackbelt Holding Zrt", "4 689 - 7 034 PLN",
                "https://nofluffjobs.com/pl/job/junior-framework-developer-blackbelt-holding-zrt-budapest-9wbjcnzy");
    }

    default OfferDto offerDtoWithBlancAndEmptyFields() {
        OfferDto offerDto = new OfferDto();
        offerDto.setOfferUrl("offerUrl");
        offerDto.setTitle("");
        offerDto.setCompany("company");
       return offerDto;
    }

    default String argumentNotValidResponse() {
       return "{\"salary\":\"can not be null\"}";
    }

    default List<OfferDto> sampleListOfOfferDto() {
        return List.of(sampleOfferDto1(), sampleOfferDto2());
    }

    default String sampleUrlForId() {
        return "/offers/1";
    }

    default String anyId() {
        return ArgumentMatchers.anyString();
    }
}
