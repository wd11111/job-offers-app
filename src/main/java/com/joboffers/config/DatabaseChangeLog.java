package com.joboffers.config;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.joboffers.model.Offer;
import com.joboffers.offer.OfferRepository;
import com.joboffers.security.model.AppUser;
import com.joboffers.security.repository.UserRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangeLog {
    @ChangeSet(order = "001", id = "seedDatabase", author = "wd")
    public void seedDatabase(OfferRepository offerRepository) {
        offerRepository.insert(List.of(sampleDtoOffer1(), sampleDtoOffer2()));
    }

    @ChangeSet(order = "002", id = "addUser", author = "wd")
    public void addUser(UserRepository userRepository) {
        AppUser appUser = new AppUser();
        appUser.setUsername("admin");
        appUser.setPassword("password");
        userRepository.insert(appUser);
    }

    private Offer sampleDtoOffer1() {
        final Offer sampleDtoOffer1 = new Offer();
        sampleDtoOffer1.setOfferUrl("https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn");
        sampleDtoOffer1.setTitle("Software Engineer - Mobile (m/f/d)");
        sampleDtoOffer1.setSalary("4k - 8k PLN");
        sampleDtoOffer1.setCompany("Cybersource");
        return sampleDtoOffer1;
    }

    private Offer sampleDtoOffer2() {
        final Offer sampleDtoOffer2 = new Offer();
        sampleDtoOffer2.setId("6321d387c9db7f57affe5049");
        sampleDtoOffer2.setOfferUrl("https://nofluffjobs.com/pl/job/junior-devops-engineer-cdq-poland-wroclaw-gnymtxqd");
        sampleDtoOffer2.setTitle("Junior DevOps Engineer");
        sampleDtoOffer2.setSalary("8k - 14k PLN");
        sampleDtoOffer2.setCompany("CDQ Poland");
        return sampleDtoOffer2;
    }

}

