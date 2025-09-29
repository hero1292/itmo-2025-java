package ru.aleksanyan.spring_database.cli.command;

public interface ConsoleCommand {
    int key();
    String description();
    void execute();
}
