package com.profile.service.ServiceImplementation;

import com.profile.models.dto.adminAccessDTO.FunctionsDTO;
import com.profile.models.dto.userDTO.*;
import com.profile.models.entity.User;
import com.profile.models.enums.RolesEnum;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.BlackListService;
import com.profile.service.serviceAnotation.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements  UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BlackListService blackListService;
    private final ModelMapper modelMapper;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, BlackListService blackListService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.blackListService = blackListService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void userRegister(UserRegisterDTO userRegisterDTO, MultipartFile file) {
            User newUser = new User();
            newUser.setUsername(userRegisterDTO.getUsername().trim().toLowerCase());
            newUser.setEmail(userRegisterDTO.getEmail().trim().toLowerCase());
            newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
            if (userRepository.count()==0){
                newUser.setRole(RolesEnum.ADMIN);
            }else {
                newUser.setRole(RolesEnum.USER);
            }
            newUser.setBanned(false);if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);
            try {
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("File upload failed");
            }
            newUser.setImage(fileName);

            }else {
                newUser.setImage("default.jpg");
            }
            this.userRepository.save(newUser);
    }

    public boolean isUsernameValid(UserRegisterDTO userRegisterDTO) {
            User foundUser = this.userRepository.findByUsername(userRegisterDTO.getUsername().trim().toLowerCase()).orElse(null);
            return foundUser != null;
        }

    @Override
    public User getUserByUsername(String username) {
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
    public List<User> getAllUsers() {
        return this.userRepository.findAll().stream()
                .filter(user -> !user.getUsername().equals("NotRegister"))
                .toList();
    }

    @Override
    public List<AllUsersDTO> viewAllRegisteredUsers() {
        return this.userRepository.findAll().stream().map(user -> new AllUsersDTO(
                user.getUsername(),
                user.getImage(),
                user.getAge(),
                user.getCity()))
                .filter(user -> !user.getUsername().equals("NotRegister"))
                .toList();
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public MyProfileDTO getProfileInfo(Long id) {
        User thisUser = this.userRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("User not found"));
            return  modelMapper.map(thisUser, MyProfileDTO.class);

    }

    @Override
    public void changeMyPassword(ChangeMyPasswordDTO changeMyPasswordDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.userRepository.findByUsername(username).get();
        user.setPassword(passwordEncoder.encode(changeMyPasswordDTO.getNewPassword()));
        this.userRepository.save(user);
    }

    @Override
    public MyProfileDTO getFullDataOfLoggedUser() {
        String loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User myProfile = this.userRepository.findByUsername(loggedUserName).get();
       return modelMapper.map(myProfile, MyProfileDTO.class);
    }

    @Override
    public void changeUserInfo(UserProfileDTO profileDTO) {
       User foundUser = this.userRepository.findByUsername(profileDTO.getUsername().trim().toLowerCase()).orElse(null);
        if (foundUser!=null) {
            foundUser.setFirstName(profileDTO.getFirstName());
            foundUser.setMiddleName(profileDTO.getMiddleName());
            foundUser.setLastName(profileDTO.getLastName());
            foundUser.setEmail(profileDTO.getEmail());
            foundUser.setCity(profileDTO.getCity());
            foundUser.setAge(profileDTO.getAge());
            this.userRepository.save(foundUser);
        } else {
            throw new NullPointerException("User not found!");
        }
    }
}
