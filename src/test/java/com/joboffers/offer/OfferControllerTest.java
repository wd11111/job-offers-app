package com.joboffers.offer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static com.joboffers.offer.ResponseBodyAssert.then;
import static org.mockito.Mockito.when;

@WebMvcTest(OfferController.class)
@TestPropertySource(properties = "mongock.enabled=false")
class OfferControllerTest implements SampleOffers {
    @MockBean
    private OfferService offerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_ok_status_when_get_for_offers() throws Exception {
        String expectedResponseBody = objectMapper.writeValueAsString(sampleListOfOfferDto());
        when(offerService.getOfferList()).thenReturn(sampleListOfOfferDto());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/offers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        then(mvcResult).hasTheSameBodyAs(expectedResponseBody);
    }

    @Test
    void should_return_ok_status_when_get_for_offer_by_id() throws Exception {
        String expectedResponseBody = objectMapper.writeValueAsString((sampleDtoOffer1()));
        when(offerService.getOfferById(anyId())).thenReturn(sampleDtoOffer1());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(getByIdUrl()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        then(mvcResult).hasTheSameBodyAs(expectedResponseBody);
    }
}

