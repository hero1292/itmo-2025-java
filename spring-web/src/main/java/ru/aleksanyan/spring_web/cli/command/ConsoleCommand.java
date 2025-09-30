package ru.aleksanyan.spring_web.cli.command;

public interface ConsoleCommand {
    int key();
    String description();
    void execute();
}
