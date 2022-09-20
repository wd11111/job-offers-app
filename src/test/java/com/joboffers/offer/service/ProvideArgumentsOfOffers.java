package com.joboffers.offer.service;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class ProvideArgumentsOfOffers implements ArgumentsProvider, Samples {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(sampleOfferDto1(), sampleOffer1(), "63223dcb1a420777c05ffd79"),
                Arguments.of(sampleOfferDto2(), sampleOffer2(), "63223dcb1a420777c05ffd7a"),
                Arguments.of(sampleOfferDto3(), sampleOffer3(), "63223dcb1a420777c05ffd7c"));
    }
}
