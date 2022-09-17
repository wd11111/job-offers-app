package com.joboffers.offer.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class OfferControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    public ResponseEntity<OfferErrorResponse> handleOfferNotFoundException(OfferNotFoundException e) {
        OfferErrorResponse offerErrorResponse = new OfferErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        log.info(e.getMessage());
        return new ResponseEntity<>(offerErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OfferDuplicateException.class)
    public ResponseEntity<OfferErrorResponse> handleOfferDuplicateException(OfferDuplicateException e) {
        OfferErrorResponse offerErrorResponse = new OfferErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
        log.info(e.getMessage());
        return new ResponseEntity<>(offerErrorResponse, HttpStatus.CONFLICT);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
            Map<String, String> errors = new HashMap<>();
            e.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongCredentials.class)
    public ResponseEntity<OfferErrorResponse> handleWrongCredentials(WrongCredentials e) {
        OfferErrorResponse offerErrorResponse = new OfferErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        log.info(e.getMessage());
        return new ResponseEntity<>(offerErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
