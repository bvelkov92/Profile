package com.profile.controller;

import com.profile.service.serviceAnotation.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
