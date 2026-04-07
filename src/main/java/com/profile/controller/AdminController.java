package com.profile.controller;

import com.profile.models.dto.FunctionsDTO;
import com.profile.models.dto.RoleDTO.ChangeRoleDTO;
import com.profile.models.entity.Functions;
import com.profile.models.entity.User;
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

import java.util.InvalidPropertiesFormatException;
import java.util.List;


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
    public List<String> adminFunctions() {
        return List.of("Change role", "Delete user", "Ban user", "Unban user");
    }



    @GetMapping("/panel")
    public String getAdminPanelPage(Model model){
        model.addAttribute("allUsers", this.userService.allUsers());
        if (!model.containsAttribute("functionsDTO")) {
            model.addAttribute("functionsDTO", new FunctionsDTO());
        }
        return "admin-panel";
    }

    @PostMapping("/panel/submit")
    public String postChangeRolePage(@Valid FunctionsDTO functionsDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes){


        if (functionsDTO.getFunctionName().equals("Change role")){
            RolesEnum currentRole = this.userService.getUsername(functionsDTO.getUsername()).getRole();
            User foundUsername = this.userService.getUsername(functionsDTO.getUsername());

            if (foundUsername.getRole().equals(RolesEnum.ADMIN) || foundUsername.getId()==1){
                bindingResult.reject
                        ("error", "Admin Role cannot be changed");
            }else if (currentRole.equals(functionsDTO.getRole())){
                bindingResult.reject
                        ("error", "Unavailable role!");
            }
        }else if (functionsDTO.getFunctionName().equals("Ban user")){
            User foundUser = this.userService.getUsername(functionsDTO.getUsername());
            if (foundUser.isBanned()){
                bindingResult.reject("error", "This username is already banned!");
            } else if (foundUser.getRole().equals(RolesEnum.ADMIN)){
                bindingResult.reject("error", "This username cannot be banned!");
            }
        } else if (functionsDTO.getFunctionName().equals("Unban user")) {
            User foundUser = this.userService.getUsername(functionsDTO.getUsername());
            if (!foundUser.isBanned()){
                bindingResult.reject("error", "This username is not banned!");
            }
        }else if (functionsDTO.getFunctionName().equals("Delete user")){
            User foundUser = this.userService.getUsername(functionsDTO.getUsername());
            if      (foundUser==null
                    || foundUser.getRole().equals(RolesEnum.ADMIN)
                    || foundUser.getRole().name().equals(functionsDTO.getRole().name())){
                bindingResult.reject("error", "This function is not allowed!");
            }
        }

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("functionDTO", functionsDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.functionDTO");
            return "redirect:/admin/panel";
        }
            this.userService.executeAdminAction(functionsDTO);
            return "redirect:/admin/panel";
    }
}
