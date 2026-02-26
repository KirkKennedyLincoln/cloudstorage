package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;

@Service
public class FileRestService {
    
    private final FileMapper fileMapper;

    public FileRestService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    
}
