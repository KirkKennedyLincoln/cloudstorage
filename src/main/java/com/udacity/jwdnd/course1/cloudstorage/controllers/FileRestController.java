package com.udacity.jwdnd.course1.cloudstorage.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

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
}
