package ru.aleksanyan.spring_database.cli.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_database.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_database.cli.command.util.IO;
import ru.aleksanyan.spring_database.service.RegionService;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class UpdateRegionCommand implements ConsoleCommand {
    private final RegionService regionService;
    private final IO io;

    @Override
    public int key() {
        return 3;
    }

    @Override
    public String description() {
        return "Обновить регион";
    }

    @Override
    public void execute() {
        String code = io.ask("Код региона: ");
        var region = regionService.byCode(code)
                .orElseThrow(() -> new RuntimeException("Регион не найден"));
        String nameRu = io.ask("Новое имя RU: ");
        String nameEn = io.ask("Новое имя EN: ");

        var updated = regionService.update(region.getId(), code, nameRu, nameEn);

        io.println("Обновлено: " + updated);
    }
}
