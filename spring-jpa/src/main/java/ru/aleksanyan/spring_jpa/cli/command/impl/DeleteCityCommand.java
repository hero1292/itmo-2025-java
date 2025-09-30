package ru.aleksanyan.spring_jpa.cli.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_jpa.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_jpa.cli.command.util.IO;
import ru.aleksanyan.spring_jpa.service.CityService;

@Component
@RequiredArgsConstructor
public class DeleteCityCommand implements ConsoleCommand {
    private final CityService cityService;
    private final IO io;

    @Override
    public int key() {
        return 10;
    }

    @Override
    public String description() {
        return "Удалить город";
    }

    @Override
    public void execute() {
        String code = io.ask("Код города: ");
        boolean ok = cityService.deleteByCode(code);
        io.println(ok ? "Удалено" : "Не найдено");
    }
}
