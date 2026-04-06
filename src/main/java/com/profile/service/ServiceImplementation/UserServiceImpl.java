package com.profile.service.ServiceImplementation;

import com.profile.models.dto.RoleDTO.ChangeRoleDTO;
import com.profile.models.dto.userDTO.UserRegisterDTO;
import com.profile.models.entity.User;
import com.profile.models.enums.RolesEnum;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void userRegister(UserRegisterDTO userRegisterDTO) {
            User newUser = new User();
            newUser.setUsername(userRegisterDTO.getUsername().trim().toLowerCase());
            newUser.setEmail(userRegisterDTO.getEmail().trim().toLowerCase());
            newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
            if (userRepository.count()==0){
                newUser.setRole(RolesEnum.ADMIN);
            }else {
                newUser.setRole(RolesEnum.USER);
            }
            newUser.setBanned(false);
            this.userRepository.save(newUser);
    }

    public boolean isUsernameValid(UserRegisterDTO userRegisterDTO) {
            User foundUser = this.userRepository.findByUsername(userRegisterDTO.getUsername().trim().toLowerCase()).orElse(null);
            return foundUser != null;
        }

    @Override
    public User getUsername(String username) {
        return this.userRepository.findByUsername(username.trim().toLowerCase()).orElse(null);
    }

    @Override
    public void setNewRole(ChangeRoleDTO changeRoleDTO) {
        Optional<User> foundUser = this.userRepository.findByUsername(changeRoleDTO.getUsername());
        foundUser.ifPresent(user -> user.setRole(changeRoleDTO.getRole()));
    }

    @Override
    public List<User> allUsers() {
        return this.userRepository.findAll();
    }
}
