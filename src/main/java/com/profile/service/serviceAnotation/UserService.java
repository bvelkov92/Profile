package com.profile.service.serviceAnotation;
import com.profile.models.dto.FunctionsDTO;
import com.profile.models.dto.userDTO.UserRegisterDTO;
import com.profile.models.entity.User;

import java.util.List;

public interface UserService {

    void userRegister(UserRegisterDTO userRegisterDTO);
    boolean isUsernameValid(UserRegisterDTO userRegisterDTO);
    User getUsername(String username);
    void executeAdminAction(FunctionsDTO functionsDTO);
    List<User> allUsers();
}
