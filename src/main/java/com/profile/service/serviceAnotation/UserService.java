package com.profile.service.serviceAnotation;
import com.profile.models.dto.adminAccessDTO.FunctionsDTO;
import com.profile.models.dto.userDTO.AllUsersDTO;
import com.profile.models.dto.userDTO.UserProfileDTO;
import com.profile.models.dto.userDTO.UserRegisterDTO;
import com.profile.models.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    void userRegister(UserRegisterDTO userRegisterDTO, MultipartFile file);
    boolean isUsernameValid(UserRegisterDTO userRegisterDTO);
    User getUserByUsername(String username);
    void executeAdminAction(FunctionsDTO functionsDTO);
    List<User> getAllUsers();
    List<AllUsersDTO> viewAllRegisteredUsers();
    User getUserById(Long id);

    void changeUserInfo(UserProfileDTO profileDTO);
}
