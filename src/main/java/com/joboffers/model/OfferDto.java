package com.joboffers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto implements Serializable {

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
