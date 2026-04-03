package com.profile.controller;

import com.profile.models.dto.UserLoginDTO;
import com.profile.service.serviceAnotation.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

   private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage(@Valid Model model){
        if (!model.containsAttribute("userLoginDTO")){
            model.addAttribute("userLoginDTO", new UserLoginDTO());
        }

        return "";
    }
}
