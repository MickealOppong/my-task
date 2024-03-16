package com.opp.todo.controller;

import com.opp.todo.service.TodoPhotoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/api/image")
public class TodoPhotoController {

    private TodoPhotoService photoService;

    public TodoPhotoController(TodoPhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/photo")
    private void add(@RequestParam("image") MultipartFile file,@RequestParam("id") Long id) throws DataFormatException, IOException {
       photoService.addImage(file,id);
    }
}
