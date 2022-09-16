package com.joboffers.security.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(message = "can not be null")
    @NotBlank(message = "can not be empty")
    @Indexed(unique = true, name = "meta_url_index_unique")
    private String username;
    @NotNull(message = "can not be null")
    @NotBlank(message = "can not be empty")
    private String password;
}
