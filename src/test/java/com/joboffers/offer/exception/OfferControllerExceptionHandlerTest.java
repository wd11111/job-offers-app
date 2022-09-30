package com.joboffers.offer.exception;

import com.joboffers.exceptionhandler.OfferControllerExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class OfferControllerExceptionHandlerTest implements SampleControllerExceptionResponse, SampleOfferNotFound {

    OfferControllerExceptionHandler offerControllerExceptionHandler = new OfferControllerExceptionHandler();

    @Test
    void should_return_correct_response_when_offer_not_found() {
        OfferNotFoundException offerNotFoundException = sampleOfferNotFoundException();
        ResponseEntity expectedOfferErrorResponse = sampleOfferNotFoundExceptionResponse();

        ResponseEntity offerErrorResponse = offerControllerExceptionHandler.handleOfferNotFoundException(offerNotFoundException);

        assertThat(offerErrorResponse).isEqualTo(expectedOfferErrorResponse);
    }
}