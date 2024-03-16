package com.opp.todo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoProject extends LogEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private TodoUser user;

    @JsonManagedReference
    @OneToMany(mappedBy = "todoProject")
    private List<TodoActivity> todoActivityList = new ArrayList<>();




    public TodoProject(String name) {
        this.name = name;
    }

    public TodoProject(String name, TodoUser user) {
        this.name = name;
        this.user = user;
    }


}
