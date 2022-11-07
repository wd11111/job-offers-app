package com.joboffers.offer.serviceunittest;

import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import com.joboffers.offer.OfferRepository;
import com.joboffers.offer.OfferService;
import com.joboffers.offer.Samples;
import com.joboffers.offer.exception.OfferDuplicateException;
import com.joboffers.offer.exception.OfferNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferServiceUnitTests implements Samples {

    @Mock
    private OfferRepository offerRepository;
    @InjectMocks
    private OfferService offerService;

    @Test
    void should_return_list_of_three_dto_offers() {
        List<OfferDto> expectedOffers = List.of(sampleOfferDto1(), sampleOfferDto2(), sampleOfferDto3());
        List<Offer> offersFromRepo = List.of(sampleOffer1(), sampleOffer2(), sampleOffer3());
        Pageable pageable = PageRequest.of(0, 5, Sort.by("title").ascending());
        when(offerRepository.findAll(pageable)).thenReturn(new PageImpl<>(offersFromRepo));

        List<OfferDto> offerList = offerService.findAll(1, "title", "asc");

        assertThat(offerList).containsAll(expectedOffers);
    }

    @ParameterizedTest(name = "expected offer dto {0}, offer from repo {1}, given id {2}")
    @ArgumentsSource(ArgumentsOfOffersProvider.class)
    void should_return_offer_dto_by_id(OfferDto expectedOffer, Offer offerFromRepo, String id) {
        when(offerRepository.findById(id)).thenReturn(Optional.of(offerFromRepo));

        OfferDto offer = offerService.findById(id);

        assertThat(offer).isEqualTo(expectedOffer);
    }

    @Test
    void should_throw_exception_when_offer_does_not_exist() {
        String sampleId = "63223dcb1a420777c05ffd7c";
        when(offerRepository.findById(sampleId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> offerService.findById(sampleId)).isInstanceOf(OfferNotFoundException.class)
                .hasMessageContaining(String.format("Offer with id %s not found", sampleId));
    }

    @Test
    void should_correctly_insert_offer() {
        OfferDto expectedOffer = sampleOfferDto1();
        Offer offerToSave = sampleOfferWithoutId1();
        Offer offerFromRepo = sampleOffer1();
        when(offerRepository.save(offerToSave)).thenReturn(offerFromRepo);

        OfferDto actualOffer = offerService.addOffer(expectedOffer);

        assertThat(actualOffer).isEqualTo(expectedOffer);
        verify(offerRepository, times(1)).save(offerToSave);
    }

    @Test
    void should_throw_offer_duplicate_exception_when_offer_already_exists() {
        Offer offerToRepo = sampleOfferWithoutId1();
        OfferDto offerToSave = sampleOfferDto1();
        when(offerRepository.save(offerToRepo)).thenThrow(DuplicateKeyException.class);

        assertThatThrownBy(() -> offerService.addOffer(offerToSave)).isInstanceOf(OfferDuplicateException.class)
                .hasMessageContaining("Offer with this url already exists");
        verify(offerRepository, times(1)).save(offerToRepo);
    }

    @Test
    void should_filter_duplicated_offers_correctly_and_save() {
        List<OfferDto> listOfThreeOffersWithOneDuplicate = List.of(sampleOfferDto1(), sampleOfferDto2(), sampleOfferDto3());

        when(offerRepository.existsByOfferUrl("https://nofluffjobs.com/pl/job/remote-junior-java-developer-tutlo-yywmpzo0")).thenReturn(true);
        when(offerRepository.saveAll(anyIterable()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        List<OfferDto> offers = offerService.saveAllOffersAfterFiltered(listOfThreeOffersWithOneDuplicate);

        assertThat(offers.size()).isEqualTo(2);
    }

}
