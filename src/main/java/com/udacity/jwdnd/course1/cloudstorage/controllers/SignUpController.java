package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class SignUpController {
    private UserService userService;
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String getView(User user) {
        return "signup";
    }

    @PostMapping("/signup")
    public String postMethodName(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        Boolean result = this.userService.createUser(user);

        if (!result) {
            model.addAttribute("signupError", "Username exists already");
            return "signup";
        }

        redirectAttributes.addFlashAttribute("signupSuccess", "You successfully signed up!");
        return "redirect:/login";
    }
}
