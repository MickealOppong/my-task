package com.opp.todo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TodoRole extends LogEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String role;

    public TodoRole() {
    }

    public TodoRole(String role) {
        this.role = role;
    }
}
