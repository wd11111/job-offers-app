package com.joboffers.scheduling;

import com.joboffers.infrastructure.RemoteOfferClient;
import com.joboffers.offer.OfferService;
import com.joboffers.scheduling.HttpOfferScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.scheduling.annotation.SchedulingConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = {"delay.hours=PT2S"})
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
                .atMost(Duration.ofSeconds(3))
                .untilAsserted(() -> verify(httpOfferScheduler, atLeast(2)).saveOffersFromHttpService());
    }
}



