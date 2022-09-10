package com.joboffers.joboffers.infrastructure;

import com.joboffers.joboffers.infrastructure.offer.dto.OfferDto;

import java.util.List;

public interface RemoteOfferClient {

    List<OfferDto> getOffers();
}
