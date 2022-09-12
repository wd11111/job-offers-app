package com.joboffers.offer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class OfferControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected OfferErrorResponse handleOfferNotFoundException(OfferNotFoundException e) {
        return new OfferErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
