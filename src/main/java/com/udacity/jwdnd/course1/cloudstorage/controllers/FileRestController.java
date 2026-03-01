package com.udacity.jwdnd.course1.cloudstorage.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class FileRestController {
    private final FileService fileService;

    public FileRestController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/api/list-files")
    public List<String> getFilenames() {
        List<String> filenames = this.fileService.fetchAllFilenames();
        System.out.println(filenames);
        return filenames;
    }

    @GetMapping("/api/file-content")
    public ResponseEntity<byte[]> getFileContent(@RequestParam String filename) {
        File file = this.fileService.fetchFile(filename);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] data = file.getFiledata();
        String content = file.getContenttype();
        
        System.out.println(file.getFilename());
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"")
            .contentType(org.springframework.http.MediaType.parseMediaType(content))
            .contentLength(data.length)
            .body(data);
    }

    @PostMapping("/api/delete-file/{filename}")
    public ResponseEntity<java.util.Map<String, Object>> deleteFile(@PathVariable("filename") String filename) {
        System.out.println(filename);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).body(java.util.Map.of("success", false, "error", "User not authenticated"));
        }

        if (this.fileService.removeFile(filename)) {
            return ResponseEntity.ok(java.util.Map.of("success", true, "message", "File successfully deleted"));
        } else {
            return ResponseEntity.status(500).body(java.util.Map.of("success", false, "error", "Failed to delete file"));
        }
    }
}
