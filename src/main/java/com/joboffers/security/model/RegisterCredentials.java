package com.joboffers.security.model;

import com.joboffers.validation.FieldsValueMatch;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
})
public class RegisterCredentials {

    private String userName;
    private String password;
    private String confirmPassword;

}
