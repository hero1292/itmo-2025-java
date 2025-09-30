package ru.aleksanyan.spring_jpa.cli.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_jpa.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_jpa.cli.command.util.IO;
import ru.aleksanyan.spring_jpa.service.CityService;

@Component
@RequiredArgsConstructor
public class UpdateCityCommand implements ConsoleCommand {
    private final CityService cityService;
    private final IO io;

    @Override
    public int key() {
        return 9;
    }

    @Override
    public String description() {
        return "Обновить город";
    }

    @Override
    public void execute() {
        String code = io.ask("Код города: ");
        String nameRu = io.ask("Новое имя RU: ");
        String nameEn = io.ask("Новое имя EN: ");
        long pop = io.askLong("Население: ");

        var c = cityService.update(code, nameRu, nameEn, pop);

        io.println("Обновлено: " + c);
    }
}
