package com.joboffers.offer.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class OfferErrorResponse {

    private String message;
    private HttpStatus httpStatus;

}
