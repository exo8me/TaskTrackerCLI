package dev.exo8.ttc.model;

import java.time.LocalDate;

public final class Task
{
    private final int id;
    private final LocalDate createdAt;

    private String description;
    private Status status;

    private LocalDate updatedAt;

    public Task(final int id)
    {
        this.id = id;
        this.createdAt = LocalDate.now();
        this.updatedAt = createdAt;
        this.status = Status.TODO;
    }

    public int getId() {
        return id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", description=" + description +
                ", status=" + status +
                ", updatedAt=" + updatedAt +
                '}';
    }
}