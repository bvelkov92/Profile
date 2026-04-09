package com.profile.service.ServiceImplementation;

import com.profile.models.dto.adminAccessDTO.FunctionsDTO;
import com.profile.models.dto.userDTO.ProfileDTO;
import com.profile.models.dto.userDTO.UserRegisterDTO;
import com.profile.models.entity.User;
import com.profile.models.enums.RolesEnum;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.BlackListService;
import com.profile.service.serviceAnotation.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements  UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BlackListService blackListService;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, BlackListService blackListService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.blackListService = blackListService;
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
        return this.userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void executeAdminAction(FunctionsDTO functionsDTO) {
        User foundUser = this.userRepository.findByUsername(functionsDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found!"));


        switch (functionsDTO.getFunctionName()) {
                case "Change role" -> foundUser.setRole(functionsDTO.getRole());
                case "Ban user" -> {
                    foundUser.setBanned(true);
                    this.blackListService.addUserToBlackList(foundUser);

                }
                case "Unban user" -> {
                    foundUser.setBanned(false);
                    this.blackListService.deleteUserFromBlackList(foundUser);
                }
                case "Delete user" -> {
                    this.userRepository.delete(foundUser);
                    return;
                }
                default -> throw new NullPointerException("User not found!");
            }
            this.userRepository.save(foundUser);
        }

    @Override
    public List<User> allUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public void changeUserInfo(ProfileDTO profileDTO) {
       User foundUser = this.userRepository.findByUsername(profileDTO.getUsername().trim().toLowerCase()).orElse(null);
        if (foundUser!=null) {
            foundUser.setFirstName(profileDTO.getFirstName());
            foundUser.setMiddleName(profileDTO.getMiddleName());
            foundUser.setLastName(profileDTO.getLastName());
            this.userRepository.save(foundUser);
        } else {
            throw new NullPointerException("User not found!");
        }
    }
}
