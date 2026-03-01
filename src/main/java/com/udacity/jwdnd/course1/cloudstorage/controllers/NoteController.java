package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class NoteController {
    
    private final UserService userService;
    private final NoteService noteService;

    public NoteController(
        UserService userService,
        NoteService noteService
    ) {
        this.userService = userService;
        this.noteService = noteService;
    }


    @PostMapping("/create-note")
    public String createNote(
        @ModelAttribute Note note,
        Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (null == user) {
            model.addAttribute("credentialError", "User not authenticated");
            return "/home";
        }
        Boolean made;
        if (note.getNoteid() != null) {
            // Update existing note
            made = this.noteService.exchangeNote(note, user.getUsername());
        } else {
            // Create new note
            made = this.noteService.addNewNote(note, user.getUsername());
        }

        return "redirect:/home?tab=notes";
    }

    @PostMapping("/update-note")
    public String updateNote(
        @ModelAttribute Note note,
        Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (null == user) {
            model.addAttribute("credentialError", "User not authenticated");
            return "/home";
        }
        Boolean made = this.noteService.exchangeNote(note, user.getUsername());
        System.out.println(made);
        if (made) {
            model.addAttribute("credentialSuccess", "Credentials successfully recieved!");
        } else {
            model.addAttribute("credentialError", "Credentials not created");
        }
        return "/home";
    }
}
