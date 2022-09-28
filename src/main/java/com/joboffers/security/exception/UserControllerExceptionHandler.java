package com.joboffers.security.exception;

import com.joboffers.offer.exception.OfferErrorResponse;
import com.joboffers.security.controller.UserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice(basePackageClasses = UserController.class)
@Slf4j
public class UserControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserDuplicateException.class)
    public ResponseEntity<OfferErrorResponse> handleUserDuplicateException(UserDuplicateException e) {
        OfferErrorResponse offerErrorResponse = new OfferErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
        log.info(e.getMessage());
        return new ResponseEntity<>(offerErrorResponse, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        log.info("Validation not passed");
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
