package com.joboffers.offer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joboffers.offer.exceptions.OfferControllerExceptionHandler;
import com.joboffers.offer.exceptions.OfferNotFoundException;
import com.joboffers.offer.serviceunitests.Samples;
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

import static com.joboffers.offer.ResponseBodyAssert.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = OfferController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = MockMvcConfig.class)
//@TestPropertySource(properties = "mongock.enabled=false")

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
        when(offerService.getOfferList()).thenReturn(sampleListOfOfferDto());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/offers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        then(mvcResult).hasTheSameBodyAs(expectedResponseBody);
    }

    @Test
    void should_return_ok_status_when_get_for_offer_by_id() throws Exception {
        String expectedResponseBody = objectMapper.writeValueAsString((sampleOfferDto1()));
        when(offerService.getOfferById(anyId())).thenReturn(sampleOfferDto1());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(sampleUrlForId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        then(mvcResult).hasTheSameBodyAs(expectedResponseBody);
    }

    @Test
    void should_return_status_not_found_when_offer_does_not_exist() throws Exception {
        when(offerService.getOfferById(anyId())).thenThrow(OfferNotFoundException.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(sampleUrlForId()))
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

        then(mvcResult).hasTheSameBodyAs(requestBody);
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



