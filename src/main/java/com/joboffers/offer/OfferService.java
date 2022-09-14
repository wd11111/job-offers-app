package com.joboffers.offer;

import com.google.common.base.Strings;
import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.exceptions.OfferNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.MappedByteBuffer;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfferService {

    OfferRepository offerRepository;

    @Cacheable(value = "offers")
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

    public List<Offer> saveAll(List<Offer> offers) {
        return offerRepository.saveAll(offers);
    }

    public List<Offer> saveAllAfterFiltered(List<OfferDto> offers) {
        return offerRepository.saveAll(filterOffersBeforeSave(offers));
    }

    public Offer addOffer(OfferDto offerDto) {
        Offer offerToInsert = OfferMapper.mapToOffer(offerDto);
        return offerRepository.insert(offerToInsert);
    }

    private List<Offer> filterOffersBeforeSave(List<OfferDto> offers) {
        return offers.stream()
                .filter(offer -> !Strings.isNullOrEmpty(offer.getOfferUrl()))
                .filter(offer -> !offerRepository.existsByOfferUrl(offer.getOfferUrl()))
                .map(OfferMapper::mapToOffer)
                .collect(Collectors.toList());
    }
 }
