package dev.exo8.ttc.command;

import dev.exo8.ttc.model.Status;
import dev.exo8.ttc.service.SimpleTaskService;
import dev.exo8.ttc.service.TaskService;

import java.util.Arrays;
import java.util.List;

public final class ConsoleCommandProcessor implements CommandProcessor
{
    private final TaskService service = new SimpleTaskService();

    @Override
    public boolean execute(List<String> args)
    {
        if (args.isEmpty())
        {
            CommandHelper.printHelpMenu();
            return true;
        }

        var command = args.getFirst().toLowerCase();

        switch (command)
        {
            case "add" -> addTask(args);
            case "update" -> updateTask(args);
            case "delete" -> deleteTask(args);
            case "mark" -> markTask(args);
            case "list" -> showTasks(args);
            case "exit" -> {
                return false;
            }
            default -> CommandHelper.printHelpMenu();
        }

        return true;
    }

    private void addTask(List<String> args)
    {
        if (args.size() < 2)
        {
            System.out.println("Usage: add <description>");
            return;
        }

        var description = joinDescription(args, 1);

        if (description.isEmpty())
        {
            System.out.println("Description cannot be empty");
            return;
        }

        var task = service.addTask(description);

        System.out.println("Added: " + task);
    }

    private void updateTask(List<String> args)
    {
        if (hasMinArgs(args, 3, "update <id> <newDescription>")) {
            return;
        }

        var id = requireId(args.get(1));

        if (id == null) {
            return;
        }

        var description = joinDescription(args, 2);

        if (description.isEmpty())
        {
            System.out.println("Description cannot be empty");
            return;
        }

        var updated = service.updateTaskDescription(id, description);

        System.out.println(updated ? "Task description updated" : "Task not found: " + id);
    }

    private void deleteTask(List<String> args)
    {
        if (hasMinArgs(args, 2, "delete <id>")) {
            return;
        }

        var id = requireId(args.get(1));

        if (id == null) {
            return;
        }

        if (id == -1)
        {
            var tasks = service.getAll();

            for (var task : tasks) {
                service.deleteTask(task.getId());
            }

            System.out.println("Delete all tasks!");

            return;
        }

        var removed = service.deleteTask(id);

        System.out.println(removed != null ? "Deleted task [id = " + id + "]" : "Task not found: " + id);
    }

    private void markTask(List<String> args)
    {
        if (args.size() < 3)
        {
            System.out.println("Usage: mark <id> <newStatus>");
            return;
        }

        var id = requireId(args.get(1));

        if (id == null) {
            return;
        }

        var status = requireStatus(args.get(2));

        if (status == null) {
            return;
        }

        if (service.updateTaskStatus(id, status)) {
            System.out.println("Task status updated");
        } else {
            System.out.println("Fail in task status updated");
        }
    }

    private void showTasks(List<String> args)
    {
        if (args.isEmpty())
        {
            System.out.println("Task list is empty");
            return;
        }

        if (args.size() == 1)
        {
            var tasks = service.getAll();

            for (var task : tasks) {
                System.out.println(task);
            }

            return;
        }

        var status = requireStatus(args.get(1));

        if (status == null) {
            return;
        }

        var tasks = service.getAllByStatus(status);

        for (var task : tasks) {
            System.out.println(task);
        }
    }

    private boolean hasMinArgs(List<String> args, int min, String usage)
    {
        if (args.size() >= min) return false;
        System.out.println("Usage: " + usage);
        return true;
    }

    private Integer requireId(String value)
    {
        var id = parseId(value);

        if (id == null) {
            System.out.println("ID must be a positive integer");
        }

        return id;
    }

    private Status requireStatus(String value)
    {
        var status = parseStatus(value);

        if (status == null) {
            System.out.println(
                    "Status must be one of: " + String.join(", ", Arrays.stream(Status.values()).map(Enum::name).toList())
            );
        }

        return status;
    }

    private String joinDescription(List<String> args, int index) {
        return String.join(" ", args.subList(index, args.size())).trim();
    }

    private Integer parseId(String value) {
        try {
            var id = Integer.parseInt(value);
            return id > -2 ? id : null;
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    private Status parseStatus(String value) {
        try {
            return Status.valueOf(value.toUpperCase().replace("-", "_"));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}