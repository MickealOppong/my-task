package com.opp.todo.service;

import com.opp.todo.exceptions.ProjectNotFoundException;
import com.opp.todo.model.TodoProject;
import com.opp.todo.model.TodoUser;
import com.opp.todo.repository.TodoActivityRepository;
import com.opp.todo.repository.TodoProjectRepository;
import com.opp.todo.repository.TodoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoProjectService {

    private TodoProjectRepository projectRepository;
    private TodoActivityRepository activityRepository;

    @Autowired
    private TodoUserRepository userRepository;


    public TodoProjectService(TodoProjectRepository projectRepository,
                              TodoActivityRepository activityRepository) {
        this.projectRepository = projectRepository;
        this.activityRepository = activityRepository;
    }

    public TodoProject createProject(TodoProject todoProject){
      return projectRepository.save(todoProject);
    }

    public void deleteProject(String projectName){
       Optional<TodoProject> todoProject = projectRepository.findByName(projectName);
        todoProject.ifPresent(project -> projectRepository.delete(project));
    }

    public void editProject(String projectName,String newName){
        Optional<TodoProject> todoProject = projectRepository.findByName(projectName);
        if(todoProject.isPresent()){
            todoProject.get().setName(newName );
            projectRepository.save(todoProject.get());
        }
    }

    public TodoProject get(String projectName){
      return projectRepository.findByName(projectName)
              .orElseThrow(()-> new ProjectNotFoundException("Project name does not exist"));
    }
    public List<TodoProject> getAll(){
        return projectRepository.findAll();
    }

    public List<TodoProject> getAllByUser(TodoUser todoUser){
        return projectRepository.findByUser(todoUser);
    }
}
