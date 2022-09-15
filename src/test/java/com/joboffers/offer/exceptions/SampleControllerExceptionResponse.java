package com.joboffers.offer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface SampleControllerExceptionResponse {

    default ResponseEntity<OfferErrorResponse> sampleOfferNotFoundExceptionResponse() {
        return new ResponseEntity<>(new OfferErrorResponse("Offer with id 1 not found", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}
