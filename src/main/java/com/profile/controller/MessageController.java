package com.profile.controller;

import com.profile.models.dto.MessageDTO.MessageDTO;
import com.profile.service.serviceAnotation.MessageService;
import com.profile.service.serviceAnotation.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }


    @GetMapping("/{id}/send")
    public String getSendMessagePage(@PathVariable Long id, Model model){
        if (!model.containsAttribute("messageDTO")){
            model.addAttribute("messageDTO", this.userService.getUserById(id));
        }
        return "message-form";
    }

    @PostMapping("/{id}/send")
    public String postSendMessagePage (@PathVariable Long id, MessageDTO messageDTO,
                                       BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("messageDTO", messageDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.messageDTO", bindingResult);
            return "redirect:/profile/" +id +"send";
        }
        String sender = principal.getName();
        this.messageService.sendMsg(sender, id, messageDTO.getMessage());

        return "redirect:/profile/" +id;
    }

    @GetMapping("/messages")
    public String getMyMessagesPage(Model model){
        model.addAttribute("getLoggedUserMessages");
        return "messages";
    }

    @PostMapping("/messages/delete/{id}")
    public String deleteSelectedMessage(@PathVariable Long id){
        this.messageService.deleteMessage(id);
        return "redirect:/profile/messages";
    }


}
