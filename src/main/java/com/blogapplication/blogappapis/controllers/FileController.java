package com.blogapplication.blogappapis.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogapplication.blogappapis.payloads.FileResponse;
import com.blogapplication.blogappapis.services.FileService;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String filePath;

    @PostMapping(value = "/upload")
    public ResponseEntity<FileResponse> fileUpload(@RequestParam(value = "image") MultipartFile image) {
        try {
            String imageName = this.fileService.uploadImage(filePath, image);
            return ResponseEntity.ok(new FileResponse(imageName, "File is successfully uploaded !!"));
        } catch (IOException e) {
            ResponseEntity<FileResponse> responseEntity = new ResponseEntity<>(
                    new FileResponse(null, "File not uploaded due to server error."), HttpStatus.INTERNAL_SERVER_ERROR);
            return responseEntity;
        }

    }

    @GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable(value = "imageName") String imageName, HttpServletResponse response)
            throws IOException {
        InputStream resourceInputStream = this.fileService.getResource(filePath, imageName);
        // response.setContentTupe is used to set the content type of our response which
        // we'll send with response
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        // now since we have to serve file(in this case image). So, we'll stream down
        // our input to output stream
        StreamUtils.copy(resourceInputStream, response.getOutputStream());
    }

}
