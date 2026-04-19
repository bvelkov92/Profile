package com.profile.models.entity;

import com.profile.models.enums.RolesEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Column
    private String username;

    @NotBlank
    @Size(min = 5)
    @Column
    private String password;

    @NotBlank
    @Email
    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    private RolesEnum role;

    @Column
    private String firstName;

    @Column
    private String middleName;
    @Column
    private String lastName;
    @Column
    private boolean isBanned;
    @Column
    private String image;
    @Column
    private String city;
    @Column
    private Integer age;

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver")
    private List<Message> receivedMessages;

}
