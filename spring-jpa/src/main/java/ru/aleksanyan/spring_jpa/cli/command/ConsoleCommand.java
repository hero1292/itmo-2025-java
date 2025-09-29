package ru.aleksanyan.spring_jpa.cli.command;

public interface ConsoleCommand {
    int key();
    String description();
    void execute();
}
