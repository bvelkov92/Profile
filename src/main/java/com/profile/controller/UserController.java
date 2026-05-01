package com.profile.controller;

import com.profile.models.dto.userDTO.*;
import com.profile.service.serviceAnotation.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/search")
    public String getAllRegisteredUsersPage(Model model){
        if (!model.containsAttribute("allUsers")){
            model.addAttribute("allUsers", this.userService.viewAllRegisteredUsers());
        }
        return "all-users";
    }

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
                                   RedirectAttributes redirectAttributes, @RequestParam MultipartFile projectImage) {

        if (this.userService.isUsernameValid(userRegisterDTO)) {
            bindingResult.rejectValue("username", "usedUsername", "Username exist or is less than 5 symbols!");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
            return "redirect:/register";
        }
        this.userService.userRegister(userRegisterDTO,projectImage);

        return "index";

    }



    @GetMapping("profile/work")
    public String getWorkPage() {

        return "work";
    }

    @GetMapping("/profile")
    public String getProfileInfoPage(Model model) {
        if (!model.containsAttribute("profileDTO")) {
            model.addAttribute("profileDTO", this.userService.getFullDataOfLoggedUser());
            }
            return "account";
        }

    @GetMapping("/profile/edit")
    public String getChangeProfileInfoPage(Model model){
        if (!model.containsAttribute("profileDTO")){
            model.addAttribute("profileDTO", this.userService.getFullDataOfLoggedUser());
        }
        return "change-account";
    }


    @PostMapping ("/profile/edit")
    public String postProfileInfoPage(@Valid UserProfileDTO profileDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("profileDTO", profileDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.profileDTO", bindingResult);

            return "redirect:/profile/edit";
        }
            this.userService.changeUserInfo(profileDTO);
        return "redirect:/profile";
    }

    @GetMapping("/all")
    public String getAllUsersPage(Model model){
        if (!model.containsAttribute("allUsers")){
            model.addAttribute("allUsers", this.userService.getAllUsers());
        }
        return "all-registered-users";
    }

    @GetMapping("/profile/{id}")
    public String getSelectedProfilePage(@PathVariable Long id,Model model){
        if (!model.containsAttribute("profileDTO")){
            model.addAttribute("profileDTO", this.userService.getUserById(id));
        }
        return "other-account-view";
    }

    @GetMapping("/{id}/password")
    public String getChangeMyPasswordPage(Model model){
        if (!model.containsAttribute("changePassword")){
            model.addAttribute("changePassword", new ChangeMyPasswordDTO());
        }

        return "change-my-password.html";
    }

    @PostMapping("/{id}/password")
    public String postChangeMyPasswordPage(@PathVariable Long id,
                                           @Valid ChangeMyPasswordDTO changeMyPasswordDTO,
                                           BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("changePassword", changeMyPasswordDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePassword", bindingResult);

            return "redirect:/" + id + "/password";
        }

        try {
            this.userService.changeMyPassword(changeMyPasswordDTO);
            return "redirect:/";
        } catch (RuntimeException message) {
            redirectAttributes.addFlashAttribute("changePassword", changeMyPasswordDTO);
            redirectAttributes.addFlashAttribute("invalidPassword", true);
            return "redirect:/" + id + "/password";
        }
    }


}
