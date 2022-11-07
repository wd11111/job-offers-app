package com.joboffers.offer;

import com.google.common.base.Strings;
import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.exception.OfferDuplicateException;
import com.joboffers.offer.exception.OfferNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    @Cacheable(value = "offers")
    public List<OfferDto> findAll(int page, String field, String sortDir) {
        Pageable pageable = getPageable(page, field, sortDir);
        return offerRepository.findAll(pageable).stream()
                .map(OfferMapper::mapToOfferDto)
                .collect(Collectors.toList());
    }

    public OfferDto findById(String id) {
        return offerRepository.findById(id)
                .map(OfferMapper::mapToOfferDto)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    @CacheEvict(value = "offers", allEntries = true)
    public List<OfferDto> saveAllOffersAfterFiltered(List<OfferDto> offers) {
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

    private Pageable getPageable(int page, String field, String sortDir) {
        return PageRequest.of(page - 1, 5,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(field).ascending() : Sort.by(field).descending());
    }

}
