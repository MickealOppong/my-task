package com.opp.todo.controller;

import com.opp.todo.exceptions.UsernameAlreadyExistException;
import com.opp.todo.model.TodoUser;
import com.opp.todo.security.SignUpRequest;
import com.opp.todo.service.TodoUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sign-up")
public class SignUpController {


    private TodoUserService userService;
    private PasswordEncoder passwordEncoder;

    public SignUpController(TodoUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String register(){
        return "This endpoint for registering users";
    }
    @PostMapping("/user")
    private ResponseEntity<String> addUser(@RequestBody SignUpRequest signUpRequest){
        try{
            TodoUser newUser = new TodoUser(signUpRequest.username(),signUpRequest.name(), passwordEncoder.encode(signUpRequest.password()));
            TodoUser user= userService.add(newUser);
                return ResponseEntity.ok().body("user created");
        }catch(UsernameAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
