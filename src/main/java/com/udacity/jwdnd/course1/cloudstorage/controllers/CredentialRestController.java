package com.udacity.jwdnd.course1.cloudstorage.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.udacity.jwdnd.course1.cloudstorage.CloudStorageApplication;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class CredentialRestController {

    private final CloudStorageApplication cloudStorageApplication;

    private final AuthenticationService authenticationService;
   
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public CredentialRestController(
        CredentialService credentialService,
        EncryptionService encryptionService
    , AuthenticationService authenticationService, CloudStorageApplication cloudStorageApplication) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.authenticationService = authenticationService;
        this.cloudStorageApplication = cloudStorageApplication;
    }

    @PostMapping("/api/list-all-credentials")
    public List<Map<String, Object>> listAllCredentials() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (null == user) {
            return null;
        }

        String username = user.getUsername();
        Credential[] collection = this.credentialService.getAllCredentials(username);
        // Suggestion form Claude to send unencrypted and encrypted at the same time.
        List<Map<String, Object>> result = new ArrayList<>();
        for (Credential c : collection) {
            Map<String, Object> map = new HashMap<>();
            map.put("credentialid", c.getCredentialid());
            map.put("url", c.getUrl());
            map.put("username", c.getUsername());
            map.put("encryptedPassword", c.getPassword());
            String decrypted = c.getPassword();
            if (c.getCredentialkey() != null && c.getPassword() != null) {
                try {
                    String passResult = this.encryptionService.decryptValue(
                        c.getPassword(), c.getCredentialkey());
                    if (result != null) {
                        decrypted = passResult;
                    }
                } catch (Exception e) {
                }
            }
            map.put("decryptedPassword", decrypted);
            result.add(map);
        }

        return result;
    }

    @PostMapping("/api/delete-credential")
    public Boolean deleteCredential(@RequestParam("url") String url) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (null == user) {
            return false;
        }

        String username = user.getUsername();
        return this.credentialService.deleteCredential(url, username);
    }
}
