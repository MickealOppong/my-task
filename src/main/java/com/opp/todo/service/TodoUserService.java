package com.opp.todo.service;

import com.opp.todo.exceptions.UsernameAlreadyExistException;
import com.opp.todo.impl.TodoImageUtilImpl;
import com.opp.todo.model.TodoProject;
import com.opp.todo.model.TodoRole;
import com.opp.todo.model.TodoUser;
import com.opp.todo.repository.TodoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoUserService implements UserDetailsService {

    private TodoUserRepository userRepository;
    private TodoPhotoService photoService;
    private TodoImageUtilImpl imageUtil;

    @Autowired
    private TodoProjectService projectService;


    public TodoUserService(TodoUserRepository userRepository, TodoPhotoService photoService,
                           TodoImageUtilImpl imageUtil) {
        this.userRepository = userRepository;
        this.photoService = photoService;
        this.imageUtil = imageUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Could not find user"));
    }

    public TodoUser getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("User does not exist"));
    }
    public TodoUser getUser(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User does not exist"));
    }

    public List<TodoUser> getAll(){
        return userRepository.findAll();
    }

    public TodoUser add(TodoUser user) throws  UsernameAlreadyExistException{
       Optional<TodoUser> usernameFromDb = userRepository.findByUsername(user.getUsername());
        if(usernameFromDb.isPresent()){
            throw new UsernameAlreadyExistException(user.getUsername()+" already exist");
        }
        TodoUser todoUser = user;
        todoUser.setRole(new TodoRole("USER"));
       TodoUser savedUser= userRepository.save(todoUser);
       projectService.createProject(new TodoProject("home",user));
       return savedUser;
    }

}
