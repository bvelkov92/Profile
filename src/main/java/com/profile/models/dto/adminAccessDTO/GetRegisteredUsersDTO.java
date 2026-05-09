package com.profile.models.dto.adminAccessDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRegisteredUsersDTO {
    private Long id;
    private String username;
    private String email;
    private String role;

}
