package com.joboffers.offer;

import com.joboffers.model.OfferDto;
import com.joboffers.model.OfferResponse;
import com.joboffers.offer.exceptions.OfferNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {

    public List<OfferDto> getOfferList() {
        List<OfferDto> mappedOffers = imitationOfDb().stream()
                .map(OfferMapper::mapToOfferDto)
                .collect(Collectors.toList());
        return mappedOffers;
    }

    public OfferDto getOfferById(String id) {
        return imitationOfDb().stream()
                .filter(offer -> offer.getId().equals(id))
                .map(OfferMapper::mapToOfferDto)
                .findFirst()
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    private static List<OfferResponse> imitationOfDb() {
        return Arrays.asList(
                offerResponseSample(),
                offerResponseSample(),
                offerResponseSample(),
                offerResponseSample());
    }

    private static OfferResponse offerResponseSample() {
        return new OfferResponse("1", "Junior DevOps Engineer", "8k - 14k PLN", "8k - 14k PLN",
                "https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn");
    }
}
