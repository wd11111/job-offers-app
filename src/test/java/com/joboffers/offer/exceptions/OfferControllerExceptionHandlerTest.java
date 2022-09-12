package com.joboffers.offer.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OfferControllerExceptionHandlerTest implements SampleControllerExceptionResponse, SampleOfferNotFound {

    OfferControllerExceptionHandler offerControllerExceptionHandler = new OfferControllerExceptionHandler();

    @Test
    void should_return_correct_response_when_offer_not_found() {
        //given
        OfferNotFoundException offerNotFoundException = sampleOfferNotFoundException();
        OfferErrorResponse expectedOfferErrorResponse = sampleOfferNotFoundExceptionResponse();

        // when
        OfferErrorResponse offerErrorResponse = offerControllerExceptionHandler.handleOfferNotFoundException(offerNotFoundException);

        // then
        assertThat(offerErrorResponse).isEqualTo(expectedOfferErrorResponse);
    }
}