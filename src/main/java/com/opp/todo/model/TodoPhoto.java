package com.opp.todo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "photo")
@Data
@Builder
@AllArgsConstructor
public class TodoPhoto extends LogEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    @Lob
    @Column(name = "photo",length = 1024)
    private byte[] image;

    @OneToOne
    @JoinColumn(name = "fk_userId",referencedColumnName = "id")
    private TodoUser user;

    public TodoPhoto() {
    }

    public TodoPhoto(String name, String type, byte[] image, TodoUser user) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.user = user;
    }
}
