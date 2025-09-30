package ru.aleksanyan.spring_web.cli;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_web.cli.command.ConsoleCommand;
import ru.aleksanyan.spring_web.cli.command.util.IO;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {
    private final List<ConsoleCommand> commands;
    private final IO io;

    @Override
    public void run(String... args) {
        while (true) {
            try {
                printMenu();
                String input = io.ask("Выберите пункт: ");
                int key = Integer.parseInt(input);

                commands.stream()
                        .filter(c -> c.key() == key)
                        .findFirst()
                        .ifPresentOrElse(
                                c -> {
                                    try {
                                        c.execute();
                                    } catch (Exception e) {
                                        io.println("Ошибка: " + e.getMessage());
                                    }
                                },
                                () -> io.println("Неизвестная команда")
                        );
            } catch (NumberFormatException e) {
                io.println("Введите число.");
            } catch (Exception e) {
                io.println("Неожиданная ошибка: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        io.println("=== Справочник городов РФ ===");

        commands.stream()
                .sorted((a, b) -> Integer.compare(a.key(), b.key()))
                .forEach(c -> io.println(c.key() + ") " + c.description()));
    }
}
