package com.joboffers.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    private String id;

    @Indexed(unique = true, name = "meta_url_index_unique")
    private String username;

    private String password;
}
