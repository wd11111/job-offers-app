package com.joboffers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto implements Serializable {

    String title;
    String company;
    String salary;
    String offerUrl;
}
