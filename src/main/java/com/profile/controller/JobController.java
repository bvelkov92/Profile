package com.profile.controller;

import com.profile.service.serviceAnotation.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class JobController {


    private final UserService userService;

    public JobController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/jobs")
    public String getJobsPage(){
        return "jobs";
    }
}
