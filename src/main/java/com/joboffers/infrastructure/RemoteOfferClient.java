package com.joboffers.infrastructure;

import com.joboffers.infrastructure.offer.dto.OfferDto;

import java.util.List;

public interface RemoteOfferClient {
    List<OfferDto> getOffers();
}
