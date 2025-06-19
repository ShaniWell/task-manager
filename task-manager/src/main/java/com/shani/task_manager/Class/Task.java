package com.shani.task_manager.Class;

//@Component- controller שמתי את זה בהערה כי אין לנו צורך בזה אם לא עושים
public class Task {

    private Long id;
    private String name;
    private String description;
    private boolean completed;

    // קונסטרקטור ריק-לא מאופשר כדאי למנוע הוספה בלי name
    //public Task() {}

    public Task(String name, String description) {//הid נכנס בשכבת הRepository
        setName(name);
        setDescription(description);
        setCompleted(false);//כל משימה שמוסיפים הcompleted הוא false באופן דיפולטיבי
    }

    public Task(String name) {
        this(name, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("ID must be a non-negative number");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Name must be at most 100 characters");
        }
        this.name = name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && description.length() > 255) {
            throw new IllegalArgumentException("Description must be at most 255 characters");
        }
        this.description = (description != null) ? description.trim() : null;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        String statusIcon = completed ? "✅" : "❌";
        String descPart = (description != null && !description.isBlank()) ? " - " + description : "";
        return id + ". " + name + descPart + " [" + statusIcon + "]";
    }

}
