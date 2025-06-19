package com.shani.task_manager;

import com.shani.task_manager.Class.Task;
import com.shani.task_manager.Exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
@Component
public class ConsoleTaskUI {
    private final TaskService taskService;
    private final Scanner scanner;
    private  String taskName;
    private  String taskDescription;

    public ConsoleTaskUI(@Autowired TaskService taskService,
                         @Value("${task.name:Default Task}") String taskName,
                         @Value("${task.description:No description}") String taskDescription) {
        this.taskService = taskService;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.scanner = new Scanner(System.in);
    }

    public void runInteractiveMenu() {
        System.out.println("🎯 מערכת ניהול משימות התחילה");
        System.out.println("דבר ראשון נראה את ביצועי המערכת ומיד תוכל לשחק חופשי");
        addDemoTasks();
        addTaskFromProperties(taskName, taskDescription);
        showAllTasks();
        addTask();
        showAllTasks();
        markTaskAsCompleted();
        showAllTasks();
        demoValidation();
        deleteTask();
        System.out.println("בוא ננסה למחוק שוב את אותו מספר משימה ונראה שחוזרת לנו הודעת שגיאה");
        deleteTask();
        System.out.println("בוא ננסה לסמן אותו מספר משימה כהושלמה ונראה שחוזרת לנו שוב הודעת שגיאה");
        markTaskAsCompleted();
        System.out.println("עכשיו את יכול לעבוד עם המערכת חופשי,בהנאה😉");
        while (true) {
            printMenu();
            String command = scanner.nextLine().trim().toLowerCase();
            if (command.equals("exit")) {
                System.out.println("להתראות! 👋");
                break;
            }
            handleCommand(command);
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\nבחרי פעולה:");
        System.out.println("all - הצגת כל המשימות");
        System.out.println("add - הוספת משימה");
        System.out.println("done - סימון משימה כהושלמה");
        System.out.println("delete - מחיקת משימה");
        System.out.println("exit - יציאה");
        System.out.print(">> ");
    }

    private void handleCommand(String command) {
        switch (command) {
            case "all" -> showAllTasks();
            case "add" -> addTask();
            case "done" -> markTaskAsCompleted();
            case "delete" -> deleteTask();
            default -> System.out.println("⚠ פקודה לא מוכרת. נסי שוב.");
        }
    }

    public void addDemoTasks() {
        System.out.println("🔹 המערכת מכניסה שלוש משימות לדוגמה...");
        taskService.addTask(new Task("Buy books", "Must buy today"));
        taskService.addTask(new Task("Eating pizza", "Eating pizza"));
        taskService.addTask(new Task("Going to sleep"));
    }

    public void addTaskFromProperties(String taskName, String taskDescription) {
        System.out.println("🔹 מוסיפים משימה מתוך application.properties");
        taskService.addTask(new Task(taskName, taskDescription));
    }

    private void showAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("⚠️ אין משימות כרגע במערכת.");
        } else {
            System.out.println("\n📋 רשימת המשימות:");
            tasks.forEach(System.out::println);
        }
    }

    private void addTask() {
        System.out.println("איזה משימה אתה צריך לעשות היום?");
        System.out.print("🔹 שם המשימה: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("⚠️ שם משימה לא יכול להיות ריק.");
            return;
        }
        System.out.print("📝 תיאור המשימה(אפשר להשאיר ריק- פשוט תלחץ Enter): ");
        String description = scanner.nextLine().trim();
        try {
            Task task = description.isEmpty() ? new Task(name) : new Task(name, description);
            taskService.addTask(task);
            System.out.println("✅ המשימה נוספה בהצלחה");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ שגיאה: " + e.getMessage());
        }
    }

    private void markTaskAsCompleted() {
        Long id = readTaskId("🔹איזה משימה כבר ביצעת? מספר משימה לסימון כהושלמה: ");
        if (id == null) return;
        try {
            taskService.markTaskAsCompleted(id);
            System.out.println("✅ המשימה סומנה כהושלמה");
        } catch (TaskNotFoundException e) {
            System.out.println("❌ שגיאה: " + e.getMessage());
        }
    }

    private void deleteTask() {
        Long id = readTaskId("🔹 מספר משימה למחיקה: ");
        if (id == null) return;
        try {
            taskService.deleteTask(id);
            System.out.println("🗑️ המשימה נמחקה בהצלחה");
        } catch (TaskNotFoundException e) {
            System.out.println("❌ שגיאה: " + e.getMessage());
        }
    }

    private Long readTaskId(String message) {
        System.out.print(message);
        String input = scanner.nextLine();
        try {
            return Long.parseLong(input.trim());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ מספר מזהה לא תקין.");
            return null;
        }
    }
    public void demoValidation() {
        System.out.println("🔎 נבדוק ניסיון להוסיף משימה בלי שם:");
        try {
            taskService.addTask(new Task(""));
        } catch (IllegalArgumentException ex) {
            System.out.println("⚠️ שגיאה צפויה: " + ex.getMessage());
        }
    }
}
