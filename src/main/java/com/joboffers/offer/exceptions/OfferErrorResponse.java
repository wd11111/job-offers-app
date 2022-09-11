package com.joboffers.offer.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
public class OfferErrorResponse {

    private String message;
    private HttpStatus httpStatus;

}
