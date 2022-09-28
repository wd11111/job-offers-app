package com.joboffers.security.model;


import lombok.Data;

@Data
public class LoginCredentials {

    private final String username;
    private final String password;
}
