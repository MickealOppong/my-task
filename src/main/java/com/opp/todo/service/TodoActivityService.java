package com.opp.todo.service;

import com.opp.todo.exceptions.ActivityNotFoundException;
import com.opp.todo.model.*;
import com.opp.todo.repository.TodoActivityRepository;
import com.opp.todo.repository.TodoProjectRepository;
import com.opp.todo.repository.TodoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TodoActivityService {


    private TodoActivityRepository activityRepository;
    private TodoProjectRepository projectRepository;
    @Autowired
    private TodoUserRepository userRepository;

    public TodoActivityService(TodoActivityRepository activityRepository,TodoProjectRepository projectRepository) {
        this.activityRepository = activityRepository;
       this.projectRepository = projectRepository;
    }

    public TodoActivity createActivity(ActivityRequest activityRequest){
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        TodoUser user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Could not perform operation"));

        TodoProject userProject = projectRepository.findByNameAndUserId(activityRequest.project(), user.getId());
       TodoActivity activity = new TodoActivity(activityRequest.title(),activityRequest.description()
                ,activityRequest.dueDate(),activityRequest.priority());
      activity.setTodoProject(userProject);
     return  activityRepository.save(activity);
    }

    public void save(TodoActivity todoActivity){
        activityRepository.save(todoActivity);
    }

    public void updatedActivityStatus(Long id,boolean status){
        Optional<TodoActivity> activity = activityRepository.findById(id);
        activity.ifPresent(p->p.setCompleted(status));
        activityRepository.save(activity.get());
    }


    public void deleteActivity(Long id){
        activityRepository.deleteById(id);
    }

    public TodoActivity getActivity(Long id){
       return activityRepository.findById(id)
                .orElseThrow(()->new ActivityNotFoundException("Unable to perform operation"));
    }


    @Transactional
    public TodoActivity update(Long id,TodoEditRequest todoEditRequest){
         TodoActivity activity = getActivity(id);
         if(todoEditRequest.title() !=null){
             activity.setTitle(todoEditRequest.title());
         }
       if(todoEditRequest.description() !=null){
           activity.setDescription(todoEditRequest.description());
       }
        if(todoEditRequest.dueDate()!=null){
            activity.setDueDate(todoEditRequest.dueDate());
        }
        if(todoEditRequest.priority()!=null){
            activity.setPriority(todoEditRequest.priority());
        }

        if(todoEditRequest.project() !=null){
            TodoProject todoProject = projectRepository.findByName(todoEditRequest.project()).get();
            todoProject.getTodoActivityList().add(activity);
            activity.setTodoProject(todoProject);
        }

      return  activityRepository.save(activity);
    }
}
