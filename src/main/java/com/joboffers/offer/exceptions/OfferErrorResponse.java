package com.joboffers.offer.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@EqualsAndHashCode
@Data
public class OfferErrorResponse {

    private String message;
    private HttpStatus httpStatus;

}
