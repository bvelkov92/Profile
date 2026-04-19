package com.profile.controller;

import com.profile.models.dto.MessageDTO.MessageDTO;
import com.profile.models.dto.userDTO.SendMessageToAllAdminsDTO;
import com.profile.service.serviceAnotation.MessageService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
public class MessageController {

    private final MessageService messageService;


    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("/profile/{id}/send")
    public String getSendMessagePage(@PathVariable Long id, Model model){
        if (!model.containsAttribute("messageDTO")){
            model.addAttribute("messageDTO", new MessageDTO());
        }
        model.addAttribute("id", id);
        return "message-form";
    }

    @PostMapping("/profile/{id}/send")
    public String postSendMessagePage (@PathVariable Long id, MessageDTO messageDTO,
                                           BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes, Principal principal){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("messageDTO", messageDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.messageDTO", bindingResult);
            return "redirect:/profile/" +id +"/send";
        }
        String sender = principal.getName();
        this.messageService.sendMsg(sender, id, messageDTO.getText(), messageDTO.getSubject());

        return "redirect:/profile/" +id;
    }

    @GetMapping("/profile/messages")
    public String getMyMessagesPage(Model model){
        model.addAttribute("getLoggedUserMessages");
        return "messages";
    }

    @PostMapping("/profile/messages/delete/{id}")
    public String deleteSelectedMessage(@PathVariable Long id){
        this.messageService.deleteMessage(id);
        return "redirect:/profile/messages";
    }

    @GetMapping("/contact")
    public String getContactPage(Model model) {
        if (!model.containsAttribute("sendMessageToAllAdminsDTO")){
            model.addAttribute("sendMessageToAllAdminsDTO", new SendMessageToAllAdminsDTO());
        }
        return "contact";
    }

    @PostMapping("/contact")
    public String postSendMessageToAllAdmins(@Valid SendMessageToAllAdminsDTO sendMessageToAllAdminsDTO,
                                             BindingResult bindingResult,
                                             RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("sendMessageToAllAdminsDTO", sendMessageToAllAdminsDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sendMessageToAllAdminsDTO", bindingResult);
            return "redirect:/contact";
        }
        this.messageService.sendMsgToAllAdmins(sendMessageToAllAdminsDTO);
        return "redirect:/";
    }

    @PostMapping("/profile/messages/check/{id}")
    public String postChangeMessageStatus(@PathVariable Long id){
        this.messageService.changeMessageStatus(id);
        return "redirect:/profile/messages";
    }


}
