package com.joboffers.security.controller;

import com.joboffers.security.model.LoginCredentials;
import com.joboffers.security.model.RegisterCredentials;
import com.joboffers.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginCredentials loginCredentials) {
        userService.loadUserByUsername(loginCredentials.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterCredentials registerCredentials) {
        userService.register(registerCredentials);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("xd")
    public Object getyyy(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return authentication.getDetails();
    }
}
