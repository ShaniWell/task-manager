package com.shani.task_manager;

import com.shani.task_manager.Class.Task;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository  // הופך את המחלקה ל-Bean מנוהל ע"י Spring
public class TaskRepository {

    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);//מרוויחה שאם ירוצו כמה תהליכים במקביל לא תהיה בעיה של סנכרון

    public Task addTask(Task task) {
        task.setId(currentId.getAndIncrement());
        tasks.put(task.getId(), task);
        return task;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Optional<Task> findTaskById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public boolean markTaskAsCompleted(Long id) {
        return findTaskById(id).map(task -> {
            task.setCompleted(true);
            return true;
        }).orElse(false);
    }

    public boolean deleteTask(Long id) {
        return findTaskById(id).map(task -> {
            tasks.remove(id);
            return true;
        }).orElse(false);
    }
}

