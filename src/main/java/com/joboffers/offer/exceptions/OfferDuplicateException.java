package com.joboffers.offer.exceptions;

public class OfferDuplicateException extends RuntimeException{

    public OfferDuplicateException() {
        super(String.format("Offer with this url already exists"));
    }
}
