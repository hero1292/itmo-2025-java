package ru.aleksanyan.spring_database.cli.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_database.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_database.cli.command.util.IO;
import ru.aleksanyan.spring_database.service.CityService;

@Component
@RequiredArgsConstructor
public class ListCitiesCommand implements ConsoleCommand {
    private final CityService cityService;
    private final IO io;

    @Override
    public int key() {
        return 6;
    }

    @Override
    public String description() {
        return "Список городов";
    }

    @Override
    public void execute() {
        cityService.listAll().forEach(c -> io.println(c.toString()));
    }
}
