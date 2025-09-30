package ru.aleksanyan.spring_jpa.cli.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_jpa.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_jpa.cli.command.util.IO;
import ru.aleksanyan.spring_jpa.service.RegionService;

@Component
@RequiredArgsConstructor
public class DeleteRegionCommand implements ConsoleCommand {
    private final RegionService regionService;
    private final IO io;

    @Override
    public int key() {
        return 4;
    }

    @Override
    public String description() {
        return "Удалить регион (удалит и города)";
    }

    @Override
    public void execute() {
        String code = io.ask("Код региона: ");

        var region = regionService.byCode(code)
                .orElseThrow(() -> new RuntimeException("Регион не найден"));

        String confirm = io.ask("Вы уверены? [y/N]: ").toLowerCase();

        if (!confirm.equals("y")) {
            io.println("Отменено.");
            return;
        }

        boolean ok = regionService.delete(region.getCode());

        io.println(ok ? "Регион удалён" : "Не удалось удалить");
    }
}
