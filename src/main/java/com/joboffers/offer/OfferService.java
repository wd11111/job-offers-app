package com.joboffers.offer;

import com.google.common.base.Strings;
import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.exception.OfferDuplicateException;
import com.joboffers.offer.exception.OfferNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OfferService {

    private final OfferRepository offerRepository;

    @Cacheable(value = "offers")
    public List<OfferDto> findAll() {
        List<OfferDto> offerDtoList = offerRepository.findAll().stream()
                .map(OfferMapper::mapToOfferDto)
                .collect(Collectors.toList());
        return offerDtoList;
    }

    public OfferDto findById(String id) {
        return offerRepository.findById(id)
                .map(OfferMapper::mapToOfferDto)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    public List<Offer> saveAll(List<Offer> offers) {
        return offerRepository.saveAll(offers);
    }

    @CacheEvict(value = "offers", allEntries = true)
    public List<OfferDto> saveAllAfterFiltered(List<OfferDto> offers) {
        List<OfferDto> filteredOffers = filterOffersBeforeSave(offers);
        offerRepository.saveAll(mapToOffers(filteredOffers));
        return filteredOffers;
    }

    @CacheEvict(value = "offers", allEntries = true)
    public OfferDto addOffer(OfferDto offerDto) {
        Offer offerToInsert = OfferMapper.mapToOffer(offerDto);
        try {
            offerRepository.save(offerToInsert);
            return offerDto;
        } catch (DuplicateKeyException e) {
            throw new OfferDuplicateException();
        }
    }

    private List<OfferDto> filterOffersBeforeSave(List<OfferDto> offers) {
        return offers.stream()
                .filter(offer -> !Strings.isNullOrEmpty(offer.getOfferUrl()))
                .filter(offer -> !offerRepository.existsByOfferUrl(offer.getOfferUrl()))
                .collect(Collectors.toList());
    }

    private List<Offer> mapToOffers(List<OfferDto> offers) {
        return offers.stream()
                .map(OfferMapper::mapToOffer)
                .collect(Collectors.toList());
    }
}
