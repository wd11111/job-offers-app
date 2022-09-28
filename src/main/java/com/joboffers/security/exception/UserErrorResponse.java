package com.joboffers.security.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class UserErrorResponse {

    private String message;
    private HttpStatus httpStatus;
}
