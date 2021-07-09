package com.example.homework02;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

public class Task implements Comparable<Task>, Serializable {

    public String UUID;

    public String taskName, priority;
    public Calendar taskDate;

    public Task(String taskName, Calendar taskDate, String priority) {
        this.UUID = java.util.UUID.randomUUID().toString();
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.priority = priority;
    }

    @Override
    public int compareTo(Task task) {
        if (taskDate == null) {
            return 0;
        }
        return taskDate.compareTo(task.taskDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(UUID, task.UUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, priority, taskDate);
    }

}