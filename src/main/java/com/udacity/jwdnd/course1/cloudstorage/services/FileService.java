package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;

public class FileService {

    private FileMapper fileMapper;
   
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
}
