package com.opp.todo.model;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UserRegistration {

    private TodoUser todoUser;

    public UserRegistration(TodoUser todoUser){
        this.todoUser = todoUser;
    }

    public TodoUser toUser(PasswordEncoder passwordEncoder){
        return new TodoUser(todoUser.getUsername(),todoUser.getName(),passwordEncoder.encode(todoUser.getPassword()));
    }
}
