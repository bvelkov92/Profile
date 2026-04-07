package com.profile.service.serviceAnotation;

import com.profile.models.enums.RolesEnum;

public interface FunctionService {

    void changeUserRole(String username, RolesEnum newRole);
    void deleteUser(String username);
    void banUser(String username);
    void unbanUser(String username);
}
