package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

@RestController
public class NoteRestController {
        private final NoteService noteService;

    public NoteRestController(NoteService noteService) { 
        this.noteService = noteService;
    }

    @GetMapping("/api/list-all-notes")
    public Note[] getNotes() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (null == user) {
            return null;
        }

        Note[] notes = this.noteService.fetchAllNotetitles(user.getUsername());
        System.out.println(notes);
        return notes;
    }

    // @GetMapping("/api/note-content/{notetitle}")
    // public ResponseEntity<byte[]> getFileContent(@PathVariable String notetitle) {
    //     Note note = this.noteService.fetchNote(notetitle);
    //     if (note == null) {
    //         return ResponseEntity.notFound().build();
    //     }

    //     // byte[] data = file.getFiledata();
    //     // String content = file.getContenttype();
        
    //     // System.out.println(file.getFilename());
    //     return ResponseEntity.ok()
    //         .header("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"")
    //         .contentType(org.springframework.http.MediaType.parseMediaType(content))
    //         .contentLength(data.length)
    //         .body(data);
    // }

    @PostMapping("/api/delete-note/{notetitle}")
    public Boolean deleteFile(@PathVariable("notetitle") String notetitle) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof User)) {
            return false;
        }
        User user = (User) auth.getPrincipal();

        return this.noteService.removeNote(notetitle, user.getUsername());
    }
}
