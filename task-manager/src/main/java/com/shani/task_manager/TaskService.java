package com.shani.task_manager;

import com.shani.task_manager.Class.Task;
import com.shani.task_manager.Exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service    // הופך את המחלקה ל-Bean מנוהל ע"י Spring
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(@Autowired TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(Task task) {
      return taskRepository.addTask(task);
    }

    public List<Task> getAllTasks() {
       return taskRepository.getAllTasks();
    }

    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findTaskById(id);
    }

    public boolean markTaskAsCompleted(Long id) {
       if(!taskRepository.markTaskAsCompleted(id))
           throw new TaskNotFoundException((id));
       return true;
    }

    public boolean deleteTask(Long id) {
        if (!taskRepository.deleteTask(id))
            throw new TaskNotFoundException(id);
        return true;
    }
}

