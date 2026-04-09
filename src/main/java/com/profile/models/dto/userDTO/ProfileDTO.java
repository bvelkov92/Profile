package com.profile.models.dto.userDTO;

import com.profile.models.enums.RolesEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDTO {

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private RolesEnum role;

    @NotBlank
    @Size(min = 5)
    private String username;

    @Email
    private String email;

    private String firstName;

    private String middleName;

    private String lastName;


}
