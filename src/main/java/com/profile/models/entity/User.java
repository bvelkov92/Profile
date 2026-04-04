package com.profile.models.entity;

import com.profile.models.enums.RolesEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @NotBlank
    @Size (min = 5)
    private String username;

    @NotBlank
    @Size(min = 5)
    private String password;

    @NotBlank
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private RolesEnum role;


    private String firstName;
    private String middleName;
    private String lastName;


}
