package com.opp.todo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoActivity extends LogEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    @DateTimeFormat
    private LocalDate dueDate;
    private String priority;
    private boolean isCompleted;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taskId",referencedColumnName = "id")
    private TodoProject todoProject;

    public TodoActivity(String title, String description, LocalDate dueDate, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = false;
    }

    public TodoActivity(String title, String description, LocalDate dueDate, String priority, TodoProject todoProject) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = false;
        this.todoProject = todoProject;
    }


    public void addProject(TodoProject todoProject){
        this.todoProject = todoProject;
        todoProject.getTodoActivityList().add(this);
    }







}
