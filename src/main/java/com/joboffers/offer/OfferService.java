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

    public void add() {
       offerRepository.insert(new Offer("1", "xd", "xd", "xd", "xd"));
    }

}
