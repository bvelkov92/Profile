package com.profile.controller;

import com.profile.models.dto.MessageDTO.LoggedUserMessagesDTO;
import com.profile.models.enums.RolesEnum;
import com.profile.service.serviceAnotation.MessageService;
import com.profile.service.serviceAnotation.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.List;

@ControllerAdvice
public class GlobalModelAttributes {

    private final UserService userService;
    private final MessageService messageService;

    public GlobalModelAttributes(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @ModelAttribute("getAllRoles")
    public RolesEnum[] rolesEnums(){
        return RolesEnum.values();
    }

    @ModelAttribute("getAdminFunction")
    public List<String> adminFunctions() {
        return List.of("Change role", "Delete user", "Ban user", "Unban user");
    }

    @ModelAttribute("userId")
    public Long getLoggedUserId(Principal principal) {
        if (principal != null) {
            String user = principal.getName();
            return this.userService.getUserByUsername(user).getId();
        }
        return null;
    }


}
