package com.profile.controller;

import com.profile.models.dto.userDTO.ProfileDTO;
import com.profile.models.dto.userDTO.UserLoginDTO;
import com.profile.models.dto.userDTO.UserRegisterDTO;
import com.profile.service.serviceAnotation.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /// /============== MODEL ATTRIBUTES ======================
    @ModelAttribute("userId")
    public Long getLoggedUserId(Principal principal) {
        if (principal != null) {
            String user = principal.getName();
            return this.userService.getUsername(user).getId();
        }
        return null;
    }

    /// /============= GET MAPPINGS   AND   POST MAPPINGS ============================

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        if (!model.containsAttribute("userLoginDTO")) {
            model.addAttribute("userLoginDTO", new UserLoginDTO());
        }

        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        if (!model.containsAttribute("userRegisterDTO")) {
            model.addAttribute("userRegisterDTO", new UserRegisterDTO());
        }
        return "register";
    }

    @PostMapping("/register")
    public String postRegisterPage(@Valid UserRegisterDTO userRegisterDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        if (this.userService.isUsernameValid(userRegisterDTO)) {
            bindingResult.rejectValue("username", "usedUsername", "Username exist or is less than 5 symbols!");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
            return "redirect:/register";
        }
        this.userService.userRegister(userRegisterDTO);

        return "index";

    }

    @GetMapping("/contact")
    public String getContactPage(Model model) {

        return "contact";
    }

    @GetMapping("profile/work")
    public String getWorkPage(Model model) {

        return "work";
    }

    @GetMapping("/{userId}/profile")
    public String getProfileInfoPage(@PathVariable Long userId, Model model) {
        if (!model.containsAttribute("profileDTO")) {
            model.addAttribute("profileDTO", this.userService.getUserById(userId));
            }
            return "account";
        }

    @GetMapping("/{userId}/profile/edit")
    public String getChangeProfileInfoPage(@PathVariable Long userId, Model model){
        if (!model.containsAttribute("profileDTO")){
            model.addAttribute("profileDTO", this.userService.getUserById(userId));
        }
        return "change-account";
    }


    @PostMapping ("/{userId}/profile/edit")
    public String postProfileInfoPage(@PathVariable Long userId, ProfileDTO profileDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("profileDTO", profileDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.profileDTO", bindingResult);

            return "redirect:/" +userId +"/profile";
        }
            this.userService.changeUserInfo(profileDTO);
        return "redirect:/" +userId +"/profile";
    }

}
