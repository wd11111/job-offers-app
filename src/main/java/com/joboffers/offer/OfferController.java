package com.joboffers.offer;

import com.joboffers.model.OfferDto;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class OfferController {

    OfferService offerService;

    @GetMapping("/offers")
    ResponseEntity<List<OfferDto>> getOfferList() {
        return ResponseEntity.ok(offerService.getOfferList());
    }


    @GetMapping("/offers/{id}")
    ResponseEntity<OfferDto> getOfferByID(@PathVariable String id) {
        return ResponseEntity.ok(offerService.getOfferById(id));
    }
}

