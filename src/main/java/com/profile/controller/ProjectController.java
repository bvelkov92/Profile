package com.profile.controller;

import com.profile.models.dto.projectDTO.AddNewProjectDTO;
import com.profile.service.serviceAnotation.ProjectService;
import com.profile.service.serviceAnotation.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;


    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }


    ////============= GET MAPPINGS   AND   POST MAPPINGS ============================

    @GetMapping("/myprojects")
    public String getPortfolioPage(Model model) {
        model.addAttribute("projects", this.projectService.getMyAllProjects());
        return "project";
    }

    @GetMapping("/myprojects/new")
    public String getAddNewProject(Model model){
        if (!model.containsAttribute("addNewProjectDTO")){
            model.addAttribute("addNewProjectDTO", new AddNewProjectDTO());
        }

        return "add-new-project";
    }

    @PostMapping("/myprojects/new")
    public String postAddNewProject(@Valid AddNewProjectDTO addNewProjectDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    @RequestParam MultipartFile projectImage){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addNewProjectDTO", addNewProjectDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addNewProjectDTO", bindingResult);
        return "redirect:/myprojects/new";
        }

        this.projectService.addProject(addNewProjectDTO, projectImage);

        return "redirect:/myprojects";
    }

}
