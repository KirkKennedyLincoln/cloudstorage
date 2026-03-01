package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@RestController
public class CredentialRestController {
   
    private final CredentialService credentialService;

    public CredentialRestController(
        CredentialService credentialService
    ) {
        this.credentialService = credentialService;
    }

    @PostMapping("/api/list-all-credentials")
    public Credential[] listAllCredentials() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (null == user) {
            return null;
        }

        String username = user.getUsername();
        Credential[] collection = this.credentialService.getAllCredentials(username);

        return collection;
    }


}
