package com.joboffers.offer.exceptions;

public interface SampleOfferNotFound {

    default OfferNotFoundException sampleOfferNotFoundException() {
        return new OfferNotFoundException("1");
    }
}
