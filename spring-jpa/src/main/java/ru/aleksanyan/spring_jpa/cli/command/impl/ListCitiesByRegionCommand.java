package ru.aleksanyan.spring_jpa.cli.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_jpa.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_jpa.cli.command.util.IO;
import ru.aleksanyan.spring_jpa.service.CityService;

@Component
@RequiredArgsConstructor
public class ListCitiesByRegionCommand implements ConsoleCommand {
    private final CityService cityService;
    private final IO io;

    @Override
    public int key() {
        return 7;
    }

    @Override
    public String description() {
        return "Города по региону";
    }

    @Override
    public void execute() {
        String regCode = io.ask("Код региона: ");

        cityService.listByRegion(regCode).forEach(c -> io.println(c.toString()));
    }
}
