package com.opp.todo.security;

import com.opp.todo.model.LogEntity;
import com.opp.todo.model.TodoUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends LogEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private Instant expirationTime;
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private TodoUser user;


    public RefreshToken( String token, TodoUser user) {
        this.token = token;
        this.user = user;
    }
}
