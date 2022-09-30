package com.joboffers.offer.exception;

public class WrongCredentials extends RuntimeException{

    public WrongCredentials() {
        super(String.format("Offer with this url already exists"));
    }
}
