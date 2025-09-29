package ru.aleksanyan.spring_jpa.cli.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_jpa.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_jpa.cli.command.util.IO;
import ru.aleksanyan.spring_jpa.service.RegionService;

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

        var updated = regionService.update(region.getCode(), nameRu, nameEn);

        io.println("Обновлено: " + updated);
    }
}
