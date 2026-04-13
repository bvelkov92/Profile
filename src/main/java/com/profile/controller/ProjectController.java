package com.profile.controller;

import com.profile.models.dto.projectDTO.AddNewProjectDTO;
import com.profile.service.serviceAnotation.ProjectService;
import com.profile.service.serviceAnotation.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;


    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }


    ////============= GET MAPPINGS   AND   POST MAPPINGS ============================

    @GetMapping("/{userId}/myprojects")
    public String getPortfolioPage(@PathVariable Long userId, Model model) {
        this.projectService.getMyAllProjects(userId);
        return "project";
    }

    @GetMapping("/{userId}/myprojects/new")
    public String getAddNewProject(@PathVariable Long userId, Model model){
        if (!model.containsAttribute("addNewProjectDTO")){
            model.addAttribute("addNewProjectDTO", new AddNewProjectDTO());
        }
        return "add-new-project";
    }

}
