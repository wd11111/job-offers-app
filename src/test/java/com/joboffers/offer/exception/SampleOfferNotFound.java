package com.joboffers.offer.exception;

public interface SampleOfferNotFound {

    default OfferNotFoundException sampleOfferNotFoundException() {
        return new OfferNotFoundException("1");
    }
}
