package com.opp.todo.controller;

import com.opp.todo.exceptions.ActivityNotFoundException;
import com.opp.todo.model.ActivityRequest;
import com.opp.todo.model.TodoActivity;
import com.opp.todo.model.TodoEditRequest;
import com.opp.todo.service.TodoActivityService;
import com.opp.todo.service.TodoProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activity")
public class TodoActivityController {


    private TodoActivityService activityService;
    private TodoProjectService projectService;

    public TodoActivityController(TodoActivityService activityService, TodoProjectService projectService) {
        this.activityService = activityService;
        this.projectService = projectService;
    }

    @GetMapping("/{id}")
    public TodoActivity get(@PathVariable("id") Long id){
        return activityService.getActivity(id);
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody ActivityRequest activityRequest){
        TodoActivity savedActivity= activityService.createActivity(activityRequest);
       if(savedActivity != null){
           return ResponseEntity.ok().body("activity created");
       }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        activityService.deleteActivity(id);
        try{
            TodoActivity activity = activityService.getActivity(id);
             return ResponseEntity.badRequest().build();
        }catch (ActivityNotFoundException e){
            return ResponseEntity.ok().body("Activity deleted");
        }
    }

    @PatchMapping("/{id}")
    public String replace(@PathVariable("id") Long id, @RequestBody TodoEditRequest todoEditRequest){
       TodoActivity activity= activityService.update(id,todoEditRequest);
       return "update successful";
    }
}
