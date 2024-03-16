package com.opp.todo.repository;

import com.opp.todo.model.TodoPhoto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodoPhotoRepository extends CrudRepository<TodoPhoto,Long> {

    TodoPhoto findByUserId(Long userId);
}
