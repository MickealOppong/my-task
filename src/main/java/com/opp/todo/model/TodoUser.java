package com.opp.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
public class TodoUser extends LogEntity implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String name;
    @JsonIgnore
    private String password;
    @OneToOne(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_roleId",referencedColumnName = "id")
    private TodoRole role;

    public TodoUser() {
    }

    public TodoUser(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public TodoUser(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
        role = new TodoRole("USER");
    }

    public TodoUser(String username,String name, String password, TodoRole role) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRole()));
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
