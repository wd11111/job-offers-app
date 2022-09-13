package com.joboffers.offer;

import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.infrastructure.offer.client.OfferHttpClient;
import com.joboffers.model.Offer;
import com.joboffers.model.OfferDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/offers")
public class OfferController {

    OfferService offerService;

    @GetMapping
    ResponseEntity<List<OfferDto>> getOfferList() {
        return ResponseEntity.ok(offerService.getOfferList());
    }

    @GetMapping("/{id}")
    ResponseEntity<OfferDto> getOfferByID(@PathVariable String id) {
        return ResponseEntity.ok(offerService.getOfferById(id));
    }
}

