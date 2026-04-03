package com.profile.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginDTO {

    private String username;

    private String password;

    public void setUsername(String username) {
        this.username = username.trim().toLowerCase();
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }
}
