package ru.aleksanyan.spring_database.cli.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_database.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_database.cli.command.util.IO;
import ru.aleksanyan.spring_database.service.RegionService;

@Component
@RequiredArgsConstructor
public class ListRegionsCommand implements ConsoleCommand {
    private final RegionService regionService;
    private final IO io;

    @Override
    public int key() {
        return 2;
    }

    @Override
    public String description() {
        return "Список регионов";
    }

    @Override
    public void execute() {
        regionService.list().forEach(r -> io.println(r.toString()));
    }
}
