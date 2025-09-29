package ru.aleksanyan.spring_database.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.aleksanyan.spring_database.cli.command.util.ConsoleIO;
import ru.aleksanyan.spring_database.cli.command.util.IO;

import java.util.Scanner;

@Configuration
public class IOConfig {
    @Bean
    public IO io() {
        return new ConsoleIO(new Scanner(System.in));
    }
}
