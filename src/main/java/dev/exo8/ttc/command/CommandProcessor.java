package dev.exo8.ttc.command;

import java.util.List;

public interface CommandProcessor {
    boolean execute(List<String> args);
}