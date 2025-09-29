package ru.aleksanyan.spring_jpa.cli.command.impl;

import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_jpa.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_jpa.cli.command.util.IO;

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