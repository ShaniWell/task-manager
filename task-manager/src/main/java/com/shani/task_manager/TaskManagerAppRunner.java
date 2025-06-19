package com.shani.task_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TaskManagerAppRunner implements CommandLineRunner {
    private final ConsoleTaskUI taskUI;

    public TaskManagerAppRunner(@Autowired ConsoleTaskUI taskUI) {
        this.taskUI=taskUI;
    }
    @Override
    public void run(String... args) throws Exception {
        taskUI.runInteractiveMenu();
    }
}
