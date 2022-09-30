package com.joboffers.offer.exception;

import com.joboffers.exceptionhandler.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface SampleControllerExceptionResponse {

    default ResponseEntity<ErrorResponse> sampleOfferNotFoundExceptionResponse() {
        return new ResponseEntity<>(new ErrorResponse("Offer with id 1 not found", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}
