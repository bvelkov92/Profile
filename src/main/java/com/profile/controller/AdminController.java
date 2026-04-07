package com.profile.controller;

import com.profile.models.dto.RoleDTO.ChangeRoleDTO;
import com.profile.models.entity.Functions;
import com.profile.models.enums.RolesEnum;
import com.profile.service.serviceAnotation.FunctionService;
import com.profile.service.serviceAnotation.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final FunctionService functionService;

    public AdminController(UserService userService, FunctionService functionService) {
        this.userService = userService;
        this.functionService = functionService;
    }

    @ModelAttribute("getAllRoles")
    public RolesEnum[] rolesEnums(){
        return RolesEnum.values();
    }

    @ModelAttribute("getAdminFunction")
    public Map <String, Consumer<String>> adminFunctions() {
        Map<String, Consumer<String>> allFunctions;

    }

    @GetMapping("/panel")
    public String getAdminPanelPage(Model model){
        model.addAttribute("allUsers", this.userService.allUsers());
        return "panel";
    }

    @PostMapping("/panel/changerole")
    public String postChangeRolePage(@Valid ChangeRoleDTO changeRoleDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){


        }
        this.userService.setNewRole(changeRoleDTO);

        return null;
    }
}
