package com.joboffers.config;

import com.joboffers.offer.OfferRepository;
import com.joboffers.offer.OfferService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @MockBean
    OfferRepository offerRepository;

    @Bean
    OfferService offerService() {
        return new OfferService(offerRepository);
    }
}
