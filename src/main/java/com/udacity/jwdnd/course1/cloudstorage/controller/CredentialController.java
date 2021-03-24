package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService,UserService userService) {
        this.credentialService = credentialService;
        this.userService=userService;
    }

    @PostMapping("/addOrUpdateCredential")
    public String postCredentials(Authentication authentication, Credential credential, Model model){
        String username = authentication.getName();
        credential.setUserId(this.userService.getUserId(username));
        int rowsProcessed = this.credentialService.addOrUpdateCredential(credential);

        if (rowsProcessed<0){
            model.addAttribute("saveCredentialSuccess", false);
            model.addAttribute("saveCredentialError", true);
        }
        else {
            model.addAttribute("saveCredentialSuccess", true);
            model.addAttribute("saveCredentialError", false);
        }

        return "result";
    }


    @GetMapping("/deleteCredential")
    public String deleteCredential(@RequestParam Integer credentialid, Model model){
        int rowsProcessed = this.credentialService.deleteCredential(credentialid);

        if (rowsProcessed<0){
            //  return "redirect:result?saveError";
            model.addAttribute("saveCredentialSuccess", false);
            model.addAttribute("saveCredentialError", true);
        }
        else {
            model.addAttribute("saveCredentialSuccess", true);
            model.addAttribute("saveCredentialError", false);
        }
        return "result";
    }


}
