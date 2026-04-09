package com.profile.models.dto.adminAccessDTO;

import com.profile.models.enums.RolesEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeRoleDTO {

   private String username;

   private RolesEnum role;

}
