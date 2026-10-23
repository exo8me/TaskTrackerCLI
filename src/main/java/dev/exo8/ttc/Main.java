package dev.exo8.ttc;

import dev.exo8.ttc.command.CommandProcessor;
import dev.exo8.ttc.command.CommandHelper;
import dev.exo8.ttc.command.ConsoleCommandProcessor;

import java.util.Arrays;
import java.util.Scanner;

public final class Main
{
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final CommandProcessor COMMAND_EXECUTOR = new ConsoleCommandProcessor();

    public static void main(final String[] strings)
    {
        if (strings.length == 0)
        {
            CommandHelper.printCommandLine();

            while (true)
            {
                var args = Arrays.stream(SCANNER.nextLine().trim().split("\\s+")).toList();

                if (!COMMAND_EXECUTOR.execute(args)) {
                    break;
                }

                CommandHelper.printCommandLine();
            }
        }
        else
        {
            // todo complete this if the call is made using java -jar ttc.jar <commands>
        }
    }
}