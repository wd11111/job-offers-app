package com.joboffers.scheduling;

import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.OfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class HttpOfferScheduler {

    private final OfferService offerService;
    private final RemoteOfferClient offerClient;

    @Scheduled(fixedDelayString = "${delay.hours:PT3H}")
    public void saveOffersFromHttpService() {
        final List<OfferDto> offersFromClient = offerClient.getOffers();
        final List<OfferDto> savedOffers = offerService.saveAllOffersAfterFiltered(offersFromClient);
        log.info("Added {} offers to database", savedOffers.size());
    }
}
