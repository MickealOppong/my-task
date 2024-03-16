package com.opp.todo.controller;

import com.opp.todo.model.TodoProject;
import com.opp.todo.model.TodoUser;
import com.opp.todo.service.TodoProjectService;
import com.opp.todo.service.TodoUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class TodoProjectController {

    private TodoProjectService projectService;
    private TodoUserService userService;

    public TodoProjectController(TodoProjectService projectService, TodoUserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProject(@RequestBody TodoProject project){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        TodoUser user = userService.getUser(username);
       TodoProject todoProject1= projectService.createProject(new TodoProject(project.getName(),user));
       if(todoProject1 != null){
           return ResponseEntity.ok().body("Project add");
       }
       return ResponseEntity.badRequest().body("Unable to create project");
    }

    @GetMapping("/all")
    public List<TodoProject> all() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        TodoUser user = userService.getUser(username);
       return projectService.getAllByUser(user);
    }

}
