package com.profile.controller;

import com.profile.service.serviceAnotation.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class BlackListController {
    private final UserService userService;

    public BlackListController(UserService userService) {
        this.userService = userService;
    }


}
