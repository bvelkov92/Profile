package com.profile.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDTO {

   private String username;

    @Size(min = 6, max = 20)
    private String password;


    private String confirmPassword;

    @Email
    @NotBlank
    private String email;

}
