package com.blogapplication.blogappapis.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogapplication.blogappapis.services.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // get file name
        String fileName = file.getOriginalFilename();

        // random name generator
        String randomId = UUID.randomUUID().toString();
        // concating randomUUID with .extension
        String fileName1 = randomId + fileName.substring(fileName.lastIndexOf("."));

        // file's fullpath
        String filePath = path + "/" + fileName1;

        // create folder if not exist
        File newFile = new File(path);
        if (!newFile.exists())
            newFile.mkdirs();

        // upload or copy file to destination
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName1; // returning filename
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }

}
