package ru.aleksanyan.spring_web.cli.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_web.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_web.cli.command.util.IO;
import ru.aleksanyan.spring_web.service.CityService;

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

        var message = cityService.relinkToRegion(cityCode, regCode);

        io.println(message);
    }
}
