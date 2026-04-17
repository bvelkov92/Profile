package com.profile.models.dto.userDTO;
import com.profile.models.enums.RolesEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyProfileDTO {

    private String username;
    private String email;

    private RolesEnum role;

    private String firstName;

    private String middleName;

    private String lastName;

    private boolean isBanned;

    private String image;

    private String city;

    private Integer age;


}
