package ru.aleksanyan.spring_database.cli.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_database.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_database.cli.command.util.IO;
import ru.aleksanyan.spring_database.service.CityService;

@Component
@RequiredArgsConstructor
public class RelinkCityToRegionCommand implements ConsoleCommand {
    private final CityService cityService;
    private final IO io;

    @Override
    public int key() {
        return 8;
    }

    @Override
    public String description() {
        return "Привязать город к региону";
    }

    @Override
    public void execute() {
        String cityCode = io.ask("Код города: ");
        String regCode = io.ask("Новый код региона: ");

        var c = cityService.relinkToRegion(cityCode, regCode);

        io.println("Обновлено: " + c);
    }
}
