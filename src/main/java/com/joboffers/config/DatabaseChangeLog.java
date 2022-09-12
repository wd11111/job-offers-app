package com.joboffers.config;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.joboffers.offer.OfferRepository;

@ChangeLog
public class DatabaseChangeLog {
    @ChangeSet(order = "001", id = "seedDatabase", author = "wd")
    public void seedDatabase(OfferRepository offerRepository) {

    }
}


