package com.opp.todo.repository;

import com.opp.todo.model.TodoProject;
import com.opp.todo.model.TodoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoProjectRepository extends JpaRepository<TodoProject,Long> {
    Optional<TodoProject> findByName(String name);
    List<TodoProject> findByUser(TodoUser user);
    TodoProject findByNameAndUserId(String name,Long userId);
}
