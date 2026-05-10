package com.profile.models.dto.userDTO;

import com.profile.models.enums.RolesEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
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

    @Size(min = 3, message = "Uncorrected city")
    private String city;

    private Integer age;

}
