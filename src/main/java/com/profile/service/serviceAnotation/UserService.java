package com.profile.service.serviceAnotation;
import com.profile.models.dto.UserRegisterDTO;
import com.profile.repository.UserRepository;

public interface UserService {

    void userRegister(UserRegisterDTO userRegisterDTO);
    boolean isUsernameValid(UserRegisterDTO userRegisterDTO);
}
