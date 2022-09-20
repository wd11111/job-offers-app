package com.joboffers.scheduling;

import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.OfferService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class HttpOfferScheduler {

    private final OfferService offerService;
    private final RemoteOfferClient offerClient;

    @Scheduled(fixedDelayString = "${delay.hours:PT3H}")
    public void saveOffersFromHttpService() {
        final List<OfferDto> offersToDb = offerClient.getOffers();
        final List<Offer> savedOffers = offerService.saveAllAfterFiltered(offersToDb);
        int amountOfSavedOffers = savedOffers.size();
        log.info(String.format("Added %d offers", amountOfSavedOffers));
    }
}
