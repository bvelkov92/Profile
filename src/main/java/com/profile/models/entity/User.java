package com.profile.models.entity;

import jakarta.persistence.Entity;
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




}
