package com.joboffers.offer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joboffers.offer.OfferController;
import com.joboffers.offer.OfferRepository;
import com.joboffers.offer.OfferService;
import com.joboffers.offer.exception.OfferControllerExceptionHandler;
import com.joboffers.offer.exception.OfferNotFoundException;
import com.joboffers.offer.service.Samples;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = OfferController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = MockMvcConfig.class)
class OfferControllerTest implements Samples {
    @MockBean
    private OfferService offerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_ok_status_when_get_for_offers() throws Exception {
        String expectedResponseBody = objectMapper.writeValueAsString(sampleListOfOfferDto());
        when(offerService.findAll()).thenReturn(sampleListOfOfferDto());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/offers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ResponseBodyAssertMvc.then(mvcResult).hasTheSameBodyAs(expectedResponseBody);
    }

    @Test
    void should_return_status_ok_when_get_for_offer_by_id() throws Exception {
        String expectedResponseBody = objectMapper.writeValueAsString((sampleOfferDto1()));
        when(offerService.findById(anyId())).thenReturn(sampleOfferDto1());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(sampleUrlForId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ResponseBodyAssertMvc.then(mvcResult).hasTheSameBodyAs(expectedResponseBody);
    }

    @Test
    void should_return_status_not_found_when_offer_does_not_exist() throws Exception {
        when(offerService.findById("1")).thenThrow(OfferNotFoundException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/offers/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @Test
    void should_return_status_created_when_adding_offer() throws Exception {
        String requestBody = objectMapper.writeValueAsString(sampleOfferDto1());

        when(offerService.addOffer(sampleOfferDto1())).thenReturn(sampleOfferDto1());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/offers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        ResponseBodyAssertMvc.then(mvcResult).hasTheSameBodyAs(requestBody);
    }

    @Test
    void should_return_bad_request_status_when_request_body_does_not_pass_validation() throws Exception {
        String requestBody = objectMapper.writeValueAsString(offerDtoWithBlancAndEmptyFields());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/offers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }
}

class MockMvcConfig {

    @Bean
    OfferControllerExceptionHandler apiValidationErrorResponseHandler() {
        return new OfferControllerExceptionHandler();
    }

    @Bean
    OfferService offerService() {
        OfferRepository offerRepository = mock(OfferRepository.class);
        return new OfferService(offerRepository);
    }

    @Bean
    OfferController offerController(OfferService offerService) {
        return new OfferController(offerService);
    }
}



