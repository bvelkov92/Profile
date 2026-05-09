package com.profile.models.dto.userDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllUsersDTO {
    private String username;
    private String image;
    private Integer age;
    private String city;
    private String role;
}
