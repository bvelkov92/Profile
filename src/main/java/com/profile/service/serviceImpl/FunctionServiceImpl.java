package com.profile.service.serviceImpl;

import com.profile.models.entity.User;
import com.profile.models.enums.RolesEnum;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.FunctionService;
import org.springframework.stereotype.Service;

@Service
public class FunctionServiceImpl implements FunctionService {

    private final UserRepository userRepository;

    public FunctionServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void changeUserRole(String username, RolesEnum newRole) {
        User user = this.userRepository.findByUsername(username).orElse(null);

        if (user!=null && user.getRole()!=newRole){
            user.setRole(newRole);
        }else {
            throw new NullPointerException("User not found!");
        }
    }

    @Override
    public void deleteUser(String username) {
        User foundUser = this.findUserByUsername(username);
            this.userRepository.delete(foundUser);
    }

    @Override
    public void banUser(String username) {
        User foundUser = this.findUserByUsername(username);
        if (!isUserAdmin(foundUser) && !foundUser.isBanned()) {
            foundUser.setBanned(true);
        }
    }

    public void unbanUser(String username) {
        User foundUser = this.findUserByUsername(username);
        if (foundUser.isBanned()){
            foundUser.setBanned(false);
        }
    }

private User findUserByUsername(String username){
       return this.userRepository.findByUsername(username).orElseThrow(()-> new NullPointerException("User not Found"));
}

private boolean isUserAdmin (User user){
        return user.getRole().equals(RolesEnum.ADMIN);
}
}
