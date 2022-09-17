package com.joboffers.security.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Indexed(unique = true, name = "meta_url_index_unique")
    private String username;
    private String password;
}
