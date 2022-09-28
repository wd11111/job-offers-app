package com.joboffers.security.controller;

import com.joboffers.security.model.LoginCredentials;
import com.joboffers.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class UserController {

    private UserService userService;

    @PostMapping
    public void login(@RequestBody LoginCredentials loginCredentials) {
        userService.loadUserByUsername(loginCredentials.getUsername());
    }
}
