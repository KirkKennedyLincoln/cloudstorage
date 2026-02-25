package com.udacity.jwdnd.course1.cloudstorage.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

public class FileController {
    private FileService fileService;

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
    public ResponseEntity<String> uploadFileStreaming(@RequestPart("fileUpload") MultipartFile filePart) 
        throws IOException {
        
        Path targetPath = Paths.get("target", "files").resolve(filePart.getOriginalFilename());
        System.out.println(targetPath);
        try (InputStream inputStream = filePart.getInputStream(); OutputStream outputStream = Files.newOutputStream(targetPath)) {
            inputStream.transferTo(outputStream);

            File newFile = new File(
                "test1",
                "application/text",
                0L,
                100,
                inputStream.readAllBytes()   
            );

            fileService.addNewFile(newFile);
        }

        return ResponseEntity.ok("Upload successful: " + filePart.getOriginalFilename());
    }
}
