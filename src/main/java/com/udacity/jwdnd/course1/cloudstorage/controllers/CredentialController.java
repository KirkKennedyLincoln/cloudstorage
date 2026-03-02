package com.udacity.jwdnd.course1.cloudstorage.controllers;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class CredentialController {

    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/credential/{url}")
    public String getCredentialByUrl(
        @RequestParam("url") String url,
        Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (null == user) {
            model.addAttribute("credentialError", "User not authenticated");
            return "/home";
        }

        Credential credential = this.credentialService.getCredentialsByUrl(url, user.getUsername());
        if (null == credential) {
            model.addAttribute("credentialError", "Credential doesn't exist");
            return "/home";
        }
         
        model.addAttribute("credentialSuccess", "Credentials successfully recieved!");
        return "/home";
    }

    @PostMapping("/credential-update/{url}")
    public String updateCredentialByUrl(
        @RequestParam("credentials") Credential credentials,
        @RequestParam("url") String url,
        Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (null == user) {
            model.addAttribute("credentialError", "User not authenticated");
            return "/home";
        }

        Boolean update = this.credentialService.updateCredential(credentials, user.getUsername());
        if (update) {
            model.addAttribute("credentialSuccess", "Credentials successfully recieved!");
        } else {
            model.addAttribute("credentialError", "Credential doesn't exist");
        }
         
        return "/home";
    }

    @PostMapping("/credential-delete/{url}")
    public String updateCredentialByUrl(
        @RequestParam("url") String url,
        Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (null == user) {
            model.addAttribute("credentialError", "User not authenticated");
            return "/home";
        }

        Boolean update = this.credentialService.deleteCredential(url, user.getUsername());
        if (update) {
            model.addAttribute("credentialSuccess", "Credentials successfully recieved!");
        } else {
            model.addAttribute("credentialError", "Credential doesn't exist");
        }
         
        return "/home";
    }

    @PostMapping("/create-credential")
    public String createCredential(
        @ModelAttribute Credential credential,
        Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (null == user) {
            model.addAttribute("credentialError", "User not authenticated");
            return "/home";
        }
        if (credential.getCredentialid() != null) {
            this.credentialService.updateCredential(credential, user.getUsername());
        } else {
            credential.setCredentialkey(UUID.randomUUID().toString());
            this.credentialService.addCredentials(credential, user.getUsername());
        }
        return "redirect:/home?tab=credentials";
    }

    @PostMapping("/update-credential")
    public String updateCredential(
        @ModelAttribute Credential credential,
        Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (null == user) {
            model.addAttribute("credentialError", "User not authenticated");
            return "/home";
        }
        credential.setCredentialkey(UUID.randomUUID().toString());
        this.credentialService.updateCredential(credential, user.getUsername());

        return "redirect:/home?tab=credentials";
    }
    

}
