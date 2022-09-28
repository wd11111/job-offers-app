package com.joboffers.offer;

import com.joboffers.model.OfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping
    ResponseEntity<List<OfferDto>> getOfferList() {
        return ResponseEntity.ok(offerService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<OfferDto> getOfferByID(@PathVariable String id) {
        return ResponseEntity.ok(offerService.findById(id));
    }

    @PostMapping("/add")
    ResponseEntity<OfferDto> addOffer(@RequestBody @Valid OfferDto offerDto) {
        return new ResponseEntity<>(offerService.addOffer(offerDto), HttpStatus.CREATED);
    }
}

