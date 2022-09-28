package com.joboffers.model;

import com.joboffers.validation.FieldsValueMatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {

    @NotNull(message = "can not be null")
    @NotBlank(message = "can not be empty")
    String title;
    @NotNull(message = "can not be null")
    @NotBlank(message = "can not be empty")
    String company;
    @NotNull(message = "can not be null")
    @NotBlank(message = "can not be empty")
    String salary;
    @NotNull(message = "can not be null")
    @NotBlank(message = "can not be empty")
    String offerUrl;
}
