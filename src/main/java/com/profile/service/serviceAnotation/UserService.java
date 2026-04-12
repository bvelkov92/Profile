package com.profile.service.serviceAnotation;
import com.profile.models.dto.adminAccessDTO.FunctionsDTO;
import com.profile.models.dto.userDTO.ProfileDTO;
import com.profile.models.dto.userDTO.UserRegisterDTO;
import com.profile.models.entity.User;

import java.util.List;

public interface UserService {

    void userRegister(UserRegisterDTO userRegisterDTO);
    boolean isUsernameValid(UserRegisterDTO userRegisterDTO);
    User getUserByUsername(String username);
    void executeAdminAction(FunctionsDTO functionsDTO);
    List<User> allUsers();
    User getUserById(Long id);

    void changeUserInfo(ProfileDTO profileDTO);
}
