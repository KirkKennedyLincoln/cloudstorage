package com.udacity.jwdnd.course1.cloudstorage.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.List;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import jakarta.security.auth.message.AuthException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class FileController {

    private final UserService userService;
    private final FileService fileService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

   @GetMapping(
        value = "/download/{filename}",
        produces = org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
   public ResponseEntity<byte[]> downloadRoute(@RequestParam("filename") String filename) {
        File myFile = fileService.fetchFile(filename);
        byte[] data =  myFile.getFiledata();
        String content = myFile.getContenttype();
        int userId = myFile.getUserid();
        
        if (null == myFile || null == content)
            return ResponseEntity.notFound().build();
        
        System.out.println(myFile.getFilename());
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=\"" + myFile.getFilename() + "\"")
            .contentType(org.springframework.http.MediaType.parseMediaType(content))
            .contentLength(data.length) 
            .body(data);
    }
    

    @PostMapping("/upload")
    public String uploadFileStreaming(@RequestParam("fileUpload") MultipartFile filePart) 
        throws IOException, AuthException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        System.out.println("This Username -> " + user.getUsername());
        if (null == user) {
            // throw new AuthException();
            return "/home";
        }

        File file = this.fileService.fetchFile(filePart.getOriginalFilename());
        String newFilename = filePart.getOriginalFilename();
        if (newFilename == null || newFilename.isEmpty()) {
            return "redirect:/home";
        }
        if (file != null) {
            // throw new IOException();
            List<String> filenames = this.fileService.fetchAllFilenamesLike(filePart.getOriginalFilename());
            newFilename = file.getFilename() + " (" + (filenames.size()) + ")";
        }

        Path targetDir = Paths.get("target", "files");
        Files.createDirectories(targetDir);  // Create directory if it doesn't exist
        Path targetPath = targetDir.resolve(newFilename);
        System.out.println(targetPath);
        try (InputStream inputStream = filePart.getInputStream(); OutputStream outputStream = Files.newOutputStream(targetPath)) {
            inputStream.transferTo(outputStream);
        } catch (Exception e) {
            e.printStackTrace();  // Log the actual error
            return "redirect:/home";
        }
            
        byte[] fileBytes = Files.readAllBytes(targetPath);
        System.out.println(fileBytes);
        File newFile = new File(
            newFilename,
            filePart.getContentType(),
            filePart.getSize(),
            user.getUserId(),
            fileBytes                  
        );

        boolean result = fileService.addNewFile(newFile);
        if (result)
            return "redirect:/home";
        else
            throw new IOException();
    }    
}

