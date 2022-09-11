package com.joboffers.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponse {

    private String id;
    private String title;
    private String company;
    private String salary;
    private String offerUrl;
}
