package com.joboffers.security.controller;

import com.joboffers.security.model.LoginCredentials;
import com.joboffers.security.model.RegisterCredentials;
import com.joboffers.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public void login(@RequestBody LoginCredentials loginCredentials) {
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterCredentials registerCredentials) {
        userService.register(registerCredentials);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
