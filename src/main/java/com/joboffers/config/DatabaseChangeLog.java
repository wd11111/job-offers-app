package com.joboffers.config;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.joboffers.model.Offer;
import com.joboffers.offer.OfferRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangeLog {
    @ChangeSet(order = "001", id = "seedDatabase", author = "wd")
    public void seedDatabase(OfferRepository offerRepository) {
        offerRepository.insert(List.of(cyberSource(), cdqPoland()));
    }

    private Offer cyberSource() {
        final Offer cybersource = new Offer();
        cybersource.setUrl("https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn");
        cybersource.setTitle("Software Engineer - Mobile (m/f/d)");
        cybersource.setSalary("4k - 8k PLN");
        cybersource.setCompany("Cybersource");
        return cybersource;
    }

    private Offer cdqPoland() {
        final Offer cdqSource = new Offer();
        cdqSource.setUrl("https://nofluffjobs.com/pl/job/junior-devops-engineer-cdq-poland-wroclaw-gnymtxqd");
        cdqSource.setTitle("Junior DevOps Engineer");
        cdqSource.setSalary("8k - 14k PLN");
        cdqSource.setCompany("CDQ Poland");
        return cdqSource;
    }

}


