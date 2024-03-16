package com.opp.todo.repository;

import com.opp.todo.model.TodoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoUserRepository extends JpaRepository<TodoUser,Long> {
    Optional<TodoUser> findByUsername(String username);
    List<TodoUser> findAll();
}
