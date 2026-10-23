package dev.exo8.ttc.command;

public final class CommandHelper
{
    private static final String PREFIX = "[TaskTrackerCLI]";
    private static final String NEW_LINE = "\n";

    public static void printHelpMenu()
    {
        System.out.println(NEW_LINE + PREFIX);

        System.out.println("- Adding a new task");
        System.out.println("add <description>" + NEW_LINE); // 2

        System.out.println("- Updating and deleting tasks");
        System.out.println("update <id> <new description>");  // 3
        System.out.println("delete <id> or -1 for delete all tasks"); // 2

        System.out.println("- Marking a task as in progress or done");
        System.out.println("mark <id> in-progress"); // 2
        System.out.println("mark <id> todo"); // 2
        System.out.println("mark <id> done" + NEW_LINE); // 2

        System.out.println("- Listing all tasks");
        System.out.println("list" + NEW_LINE); // 1

        System.out.println("- Listing tasks by status");
        System.out.println("list done"); // 2
        System.out.println("list todo"); // 2
        System.out.println("list in-progress" + NEW_LINE); // 2

        printCommandLine();
    }

    public static void printCommandLine() {
        System.out.print("Enter the command: ");
    }

    private CommandHelper() {}
}