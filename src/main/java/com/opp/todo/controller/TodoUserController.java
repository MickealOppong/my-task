package com.opp.todo.controller;

import com.opp.todo.model.TodoUser;
import com.opp.todo.service.TodoUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class TodoUserController {

    private TodoUserService userService;

    public TodoUserController(TodoUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<TodoUser> getUser(Principal principal){
        return ResponseEntity.ok().body(userService.getUser(principal.getName()));
    }
}
