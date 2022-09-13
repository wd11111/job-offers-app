package com.joboffers.offer;

import com.joboffers.model.OfferDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/add/dodaj")
    ResponseEntity<Void> getOfferByIDa() {
        offerService.add();
        return ResponseEntity.ok().build();
    }
}

