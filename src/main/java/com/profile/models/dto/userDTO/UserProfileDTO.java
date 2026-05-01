package com.profile.models.dto.userDTO;

import com.profile.models.enums.RolesEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileDTO {


    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private RolesEnum role;

    @NotBlank
    @Size(min = 5)
    private String username;

    @Email(message = "Invalid email")
    private String email;

    @Pattern(regexp = "^[A-Za-zА-Яа-я]+$", message = "Only letters allowed")
    private String firstName;
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+$", message = "Only letters allowed")
    private String middleName;
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+$", message = "Only letters allowed")
    private String lastName;

    private String city;

    private Integer age;

}
