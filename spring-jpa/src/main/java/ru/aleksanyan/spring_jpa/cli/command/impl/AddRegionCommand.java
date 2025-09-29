package ru.aleksanyan.spring_jpa.cli.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_jpa.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_jpa.cli.command.util.IO;
import ru.aleksanyan.spring_jpa.domain.Region;
import ru.aleksanyan.spring_jpa.service.RegionService;

@Component
@RequiredArgsConstructor
public class AddRegionCommand implements ConsoleCommand {
    private final RegionService regionService;
    private final IO io;

    @Override
    public int key() {
        return 1;
    }

    @Override
    public String description() {
        return "Добавить регион";
    }

    @Override
    public void execute() {
        String code = io.ask("Код региона (напр. RU-MOW): ");
        String nameRu = io.ask("Название RU: ");
        String nameEn = io.ask("Название EN: ");
        Region r = regionService.create(code, nameRu, nameEn);
        io.println("Добавлен регион: " + r);
    }
}
