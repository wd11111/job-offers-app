package com.joboffers.scheduling;

import com.joboffers.JobOffersApplication;
import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.infrastructure.offer.client.OfferHttpClient;
import com.joboffers.offer.OfferService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = {"delay.hours=PT4S"})
@SpringJUnitConfig(SchedulingConfiguration.class)
class HttpOfferSchedulerTest {

    @SpyBean
    private HttpOfferScheduler httpOfferScheduler;

    @MockBean
    OfferService service;

    @MockBean
    RemoteOfferClient remoteOfferClient;

    @Test
    public void whenWaitOneSecond_thenScheduledIsCalledAtLeastTenTimes() {
        await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> verify(httpOfferScheduler, atLeast(2)).saveOffersFromHttpService());

    }
}





