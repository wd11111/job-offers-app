package com.joboffers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("offers")
public class Offer {

    @Id
    private String id;
    private String title;
    private String company;
    private String salary;
    private String url;
}

