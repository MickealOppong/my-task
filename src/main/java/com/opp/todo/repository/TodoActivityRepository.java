package com.opp.todo.repository;

import com.opp.todo.model.TodoActivity;
import com.opp.todo.model.TodoProject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoActivityRepository extends CrudRepository<TodoActivity,Long> {
    Optional<TodoActivity> findByTitle(String title);
    List<TodoActivity> findAll();
    List<TodoActivity> findByTodoProject(TodoProject project);

}
