package com.profile.controller;

import com.profile.service.serviceAnotation.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class BlackListController {
    private final UserService userService;

    public BlackListController(UserService userService) {
        this.userService = userService;
    }

    ////============== MODEL ATTRIBUTES ======================
    @ModelAttribute("userId")
    public Long getLoggedUserId(Principal principal) {
        if (principal != null) {
            String user = principal.getName();
            return this.userService.getUserByUsername(user).getId();
        }
        return null;
    }


}
