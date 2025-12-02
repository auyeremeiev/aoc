package common;

public enum Task {
    FIRST("Task 1"),
    SECOND("Task 2");

    private final String taskTextName;

    private Task(String taskTextName) {
        this.taskTextName = taskTextName;
    }

    public String getTaskTextName() {
        return taskTextName;
    }
}
