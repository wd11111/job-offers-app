package com.joboffers.offer;

import com.joboffers.model.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository extends MongoRepository<Offer, String> {

    boolean existsByOfferUrl(String offerUrl);
    Offer findByOfferUrl (String offerUrl);
}
