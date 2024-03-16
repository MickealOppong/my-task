package com.opp.todo.service;

import com.opp.todo.impl.TodoImageUtilImpl;
import com.opp.todo.model.TodoPhoto;
import com.opp.todo.repository.TodoPhotoRepository;
import com.opp.todo.repository.TodoUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

@Service
public class TodoPhotoService {

    private TodoPhotoRepository photoRepository;
    private TodoImageUtilImpl imageUtil;
    private TodoUserRepository userRepository;


    public TodoPhotoService(TodoPhotoRepository photoRepository, TodoImageUtilImpl imageUtil,
                            TodoUserRepository userRepository) {
        this.photoRepository = photoRepository;
        this.imageUtil = imageUtil;
        this.userRepository = userRepository;
    }

    public byte[] getPhoto(Long userId){
      return  photoRepository.findById(userId).map(p->p.getImage()).get();
    }


    public void addImage(MultipartFile file,String username) throws IOException, DataFormatException {
        TodoPhoto photo = TodoPhoto.builder()
                .type(file.getContentType())
                .name(file.getOriginalFilename())
                .image(imageUtil.compress(file.getBytes()))
                .user(userRepository.findByUsername(username).get()).build();
        photoRepository.save(photo);
    }
    public void addImage(MultipartFile file,Long id) throws IOException, DataFormatException {
        TodoPhoto photo = TodoPhoto.builder()
                .type(file.getContentType())
                .name(file.getOriginalFilename())
                .image(imageUtil.compress(file.getBytes()))
                .user(userRepository.findById(id).get()).build();
        photoRepository.save(photo);
    }

    public TodoPhoto get(Long userId) throws IOException, DataFormatException {

      return null;

    }
}
