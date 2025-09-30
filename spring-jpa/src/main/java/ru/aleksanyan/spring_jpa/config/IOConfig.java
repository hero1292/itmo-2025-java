package ru.aleksanyan.spring_jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.aleksanyan.spring_jpa.cli.command.util.ConsoleIO;
import ru.aleksanyan.spring_jpa.cli.command.util.IO;

import java.util.Scanner;

@Configuration
public class IOConfig {
    @Bean
    public IO io() {
        return new ConsoleIO(new Scanner(System.in));
    }
}
