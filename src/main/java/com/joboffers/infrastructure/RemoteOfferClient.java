package com.joboffers.infrastructure;

import com.joboffers.model.OfferDto;

import java.util.List;

public interface RemoteOfferClient {
    List<OfferDto> getOffers();
}
