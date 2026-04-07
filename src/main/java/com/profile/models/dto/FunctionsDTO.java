package com.profile.models.dto;

import com.profile.models.enums.RolesEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FunctionsDTO {

    private String functionName;
    private String username;
    private RolesEnum role;
}
