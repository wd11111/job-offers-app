package com.joboffers.offer;

import com.joboffers.model.OfferDto;
import com.joboffers.model.Offer;
import com.joboffers.offer.exceptions.OfferNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfferService {

    OfferRepository offerRepository;

    public List<OfferDto> getOfferList() {
        List<OfferDto> offerDtoList = offerRepository.findAll().stream()
                .map(OfferMapper::mapToOfferDto)
                .collect(Collectors.toList());
        return offerDtoList;
    }

    public OfferDto getOfferById(String id) {
        return offerRepository.findById(id)
                .map(OfferMapper::mapToOfferDto)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    private static List<Offer> imitationOfDb() {
        return Arrays.asList(
                offerResponseSample(),
                offerResponseSample(),
                offerResponseSample(),
                offerResponseSample());
    }

    private static Offer offerResponseSample() {
        return new Offer("1", "Junior DevOps Engineer", "8k - 14k PLN", "8k - 14k PLN",
                "https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn");
    }
}
