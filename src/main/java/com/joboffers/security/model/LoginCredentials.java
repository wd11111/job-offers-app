package com.joboffers.security.model;

import lombok.Data;

@Data
public class LoginCredentials {

    private String username;

    private String password;
}