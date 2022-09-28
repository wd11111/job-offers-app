package com.joboffers.offer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class OfferErrorResponse {

    private String message;
    private HttpStatus httpStatus;

}
