package com.udacity.jwdnd.course1.cloudstorage.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

@Controller
@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
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
        throws IOException {
        
        Path targetPath = Paths.get("target", "files").resolve(filePart.getOriginalFilename());
        System.out.println(targetPath);
        try (InputStream inputStream = filePart.getInputStream(); OutputStream outputStream = Files.newOutputStream(targetPath)) {
            inputStream.transferTo(outputStream);
        }
            
        byte[] fileBytes = Files.readAllBytes(targetPath);
        System.out.println(fileBytes);
        File newFile = new File(
            filePart.getOriginalFilename(),
            filePart.getContentType(),
            filePart.getSize(),
            4,
            fileBytes                  
        );

        boolean result = fileService.addNewFile(newFile);
        System.out.println("Result from fileService " + result);
        return "redirect:/home";
    }

    @GetMapping("/api/list-files")
    public List<String> getFilenames() {
        List<String> filenames = this.fileService.fetchAllFilenames();
        System.out.println(filenames);
        return filenames;
    }
    
}
