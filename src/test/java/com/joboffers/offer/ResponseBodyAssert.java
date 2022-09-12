package com.joboffers.offer;

import lombok.AllArgsConstructor;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor
public class ResponseBodyAssert {

    private final MvcResult mvcResult;

    static ResponseBodyAssert then (MvcResult mvcResult) {
        return new ResponseBodyAssert(mvcResult);
    }

    ResponseBodyAssert hasTheSameBodyAs(String expectedBody) throws UnsupportedEncodingException {
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedBody);
        return this;
    }
}
