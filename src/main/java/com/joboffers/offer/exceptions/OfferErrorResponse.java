package com.joboffers.offer.exceptions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@EqualsAndHashCode
public class OfferErrorResponse {

    private String message;
    private HttpStatus httpStatus;

}
