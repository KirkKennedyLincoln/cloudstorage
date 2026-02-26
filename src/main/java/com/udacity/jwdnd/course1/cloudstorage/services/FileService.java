package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }
   
    public Boolean addNewFile(File file) {
        int addedFile = fileMapper.insertFile(file);
        
        return addedFile > 0;
    }

    public Boolean removeFile(String filename) {
        int fileId = fileMapper.getFileIdByFilename(filename);
        return fileMapper.deleteFileByFileId(fileId);
    }

    public Boolean exchangefile(File file) {
        int fileId = fileMapper.getFileIdByFilename(file.getFilename());
        return fileMapper.updateFileByFileId(fileId, file);
    }

    public File fetchFile(String filename) {
        return fileMapper.getFileByFilename(filename);
    }

    public List<String> fetchAllFilenames() {
        List<String> allfiles = fileMapper.getAllFilenames();
        System.out.println(allfiles);
        return allfiles;
    }
}
