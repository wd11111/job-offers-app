package com.joboffers.offer;

import com.joboffers.model.OfferDto;
import org.mockito.ArgumentMatchers;

import java.util.List;

interface SamplesForOfferController {

    default OfferDto sampleOfferDto() {
        return new OfferDto("Junior DevOps Engineer", "CDQ Poland", "8k - 14k PLN", "url");
    }

    default List<OfferDto> sampleListOfOfferDto() {
        return List.of(sampleOfferDto(), sampleOfferDto());
    }

    default String getByIdUrl() {
        return "/offers/1";
    }

    default String anyId() {
        return ArgumentMatchers.anyString();
    }
}
