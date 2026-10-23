package dev.exo8.ttc.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.exo8.ttc.model.Status;
import dev.exo8.ttc.model.Task;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class SimpleTaskService implements TaskService
{
    private static final String TASK_FILE_NAME = "tasks.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Type TYPE = new TypeToken<List<Task>>(){}.getType();

    private final File file;

    public SimpleTaskService()
    {
        this.file = new File(TASK_FILE_NAME);

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created");
                } else {
                    System.out.println("File not created!");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Task addTask(String description)
    {
        var tasks = readTasks();
        var task = new Task(getNextId());

        task.setDescription(description);
        tasks.add(task);
        writeTasks(tasks);

        return task;
    }

    @Override
    public boolean updateTaskDescription(int id, String newDescription)
    {
        var tasks = readTasks();

        for (var task : tasks)
        {
            if (task.getId() != id) continue;

            task.setDescription(newDescription);
            task.setUpdatedAt(LocalDate.now());

            writeTasks(tasks);
            return true;
        }

        return false;
    }

    @Override
    public Task deleteTask(int id)
    {
        var tasks = readTasks();

        for (var task : new ArrayList<>(tasks))
        {
            if (task.getId() != id) continue;

            tasks.remove(task);
            writeTasks(tasks);

            return task;
        }

        return null;
    }

    @Override
    public boolean updateTaskStatus(int id, Status newStatus)
    {
        var tasks = readTasks();

        for (var task : tasks)
        {
            if (task.getId() != id) continue;

            task.setStatus(newStatus);
            task.setUpdatedAt(LocalDate.now());

            writeTasks(tasks);
            return true;
        }

        return false;
    }

    @Override
    public List<Task> getAll() {
        return readTasks();
    }

    @Override
    public List<Task> getAllByStatus(Status status) {
        return readTasks().stream().filter(task -> task.getStatus() == status).toList();
    }

    @Override
    public int getNextId()
    {
        var ids = readTasks().stream().mapToInt(Task::getId).sorted().toArray();

        var nextId = 0;

        for (var id : ids)
        {
            if (id == nextId) {
                nextId++;
            }
            else if (id > nextId){
                break;
            }
        }

        return nextId;
    }

    private List<Task> readTasks()
    {
        try
        {
            var json = Files.readString(file.toPath());

            if (json.isBlank()) {
                return new ArrayList<>();
            }

            var tasks = (List<Task>) GSON.fromJson(json, TYPE);

            return tasks == null ? new ArrayList<>() : tasks;
        }
        catch (IOException e) {
            throw new RuntimeException("Fail on read file");
        }
    }

    private void writeTasks(List<Task> tasks)
    {
        try {
            Files.writeString(file.toPath(), GSON.toJson(tasks, TYPE));
        } catch (IOException e) {
            throw new RuntimeException("Fail on save file");
        }
    }
}