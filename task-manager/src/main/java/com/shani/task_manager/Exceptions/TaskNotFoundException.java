package com.shani.task_manager.Exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("❌ לא נמצאה משימה עם מספר מזהה: " + id + ". ודא שהמספר שהזנת נכון.");
    }
}
