package dev.exo8.ttc.service;

import dev.exo8.ttc.model.Status;
import dev.exo8.ttc.model.Task;

import java.util.List;

public interface TaskService
{
    Task addTask(String description);

    boolean updateTaskDescription(int id, String newDescription);
    Task deleteTask(int id);

    boolean updateTaskStatus(int id, Status newStatus);

    List<Task> getAll();
    List<Task> getAllByStatus(Status status);

    int getNextId();
}