package ru.aleksanyan.spring_database.cli.command.impl;

import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_database.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_database.cli.command.util.IO;

import java.util.Scanner;

@Component
public class ExitCommand implements ConsoleCommand {
    private final IO io;

    public ExitCommand(IO io) {
        this.io = io;
    }

    @Override
    public int key() {
        return 0;
    }

    @Override
    public String description() {
        return "Выход";
    }

    @Override
    public void execute() {
        io.println("Выход.");

        System.exit(0);
    }
}