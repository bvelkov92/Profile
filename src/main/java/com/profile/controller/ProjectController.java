package com.profile.controller;

import com.profile.models.dto.projectDTO.AddNewProjectDTO;
import com.profile.service.serviceAnotation.ProjectService;
import com.profile.service.serviceAnotation.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;


    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    ////============== MODEL ATTRIBUTES ======================
    @ModelAttribute("userId")
    public Long getLoggedUserId(Principal principal) {
        if (principal != null) {
            String user = principal.getName();
            return this.userService.getUsername(user).getId();
        }
        return null;
    }

    ////============= GET MAPPINGS   AND   POST MAPPINGS ============================

    @GetMapping("/{loggedUserId}/myprojects")
    public String getPortfolioPage(@PathVariable Long loggedUserId, Model model) {
        this.projectService.getMyAllProjects(loggedUserId);
        return "portfolio";
    }

    @GetMapping("/{loggedUserId}/myprojects/new")
    public String getAddNewProject(@PathVariable Long loggedUserId, Model model){
        if (!model.containsAttribute("addNewProjectDTO")){
            model.addAttribute("addNewProjectDTO", new AddNewProjectDTO());
        }
        return "add-new-project";
    }

}
