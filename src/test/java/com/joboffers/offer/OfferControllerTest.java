package com.joboffers.offer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joboffers.model.OfferDto;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(OfferController.class)
class OfferControllerTest {

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
        String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(expectedResponseBody).isEqualTo(responseBody);
    }

    @Test
    void should_return_ok_status_when_get_for_offer_by_id() throws Exception {
        String expectedResponseBody = objectMapper.writeValueAsString((sampleOfferDto()));
        when(offerService.getOfferById(ArgumentMatchers.anyString())).thenReturn(sampleOfferDto());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/offers/" + ArgumentMatchers.any()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(expectedResponseBody).isEqualTo(responseBody);
    }

    private OfferDto sampleOfferDto() {
        return new OfferDto("Junior DevOps Engineer", "CDQ Poland", "8k - 14k PLN", "url");
    }

    private List<OfferDto> sampleListOfOfferDto() {
        return List.of(sampleOfferDto(), sampleOfferDto());
    }


}