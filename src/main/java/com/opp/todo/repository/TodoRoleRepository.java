package com.opp.todo.repository;

import com.opp.todo.model.TodoRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRoleRepository extends CrudRepository<TodoRole,Long> {
    Optional<TodoRole> findByRole(String role);
}
