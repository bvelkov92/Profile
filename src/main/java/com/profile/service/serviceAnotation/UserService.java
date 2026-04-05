package com.profile.service.serviceAnotation;
import com.profile.models.dto.userDTO.UserRegisterDTO;
import com.profile.models.entity.User;

public interface UserService {

    void userRegister(UserRegisterDTO userRegisterDTO);
    boolean isUsernameValid(UserRegisterDTO userRegisterDTO);
    User getUsername(String username);
}
