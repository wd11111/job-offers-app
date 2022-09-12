package com.joboffers.offer.exceptions;

import org.springframework.http.HttpStatus;

public interface SampleControllerExceptionResponse {

    default OfferErrorResponse sampleOfferNotFoundExceptionResponse() {
        return new OfferErrorResponse("Offer with id 1 not found", HttpStatus.NOT_FOUND);
    }
}
