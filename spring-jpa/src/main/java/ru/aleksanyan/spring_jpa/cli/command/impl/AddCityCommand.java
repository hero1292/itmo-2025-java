package ru.aleksanyan.spring_jpa.cli.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_jpa.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_jpa.cli.command.util.IO;
import ru.aleksanyan.spring_jpa.service.CityService;

@Component
@RequiredArgsConstructor
public class AddCityCommand implements ConsoleCommand {
    private final CityService cityService;
    private final IO io;

    @Override
    public int key() {
        return 5;
    }

    @Override
    public String description() {
        return "Добавить город";
    }

    @Override
    public void execute() {
        String code = io.ask("Код города (напр. MOW): ");
        String nameRu = io.ask("Название RU: ");
        String nameEn = io.ask("Название EN: ");
        long pop = io.askLong("Численность: ");
        String regCode = io.ask("Код региона (напр. RU-MOW) [Enter — пропустить]: ");

        var city = cityService.create(code, nameRu, nameEn, pop, regCode);
        io.println("Добавлен город: " + city);
    }
}
