package ru.aleksanyan.javaadvance.lesson3.cli;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.aleksanyan.javaadvance.lesson3.core.FibonacciService;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

@Slf4j
@Component
@RequiredArgsConstructor
public class FibRunner implements CommandLineRunner {
    private final FibonacciService fibonacciService;

    @Override
    public void run(String... args) {
        System.out.println("=== Fibonacci Console ===");
        System.out.println("Введите n (целое n >= 0), 'clear' для очистки кэша, 'exit' или 'q' для выхода.");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("n = ");

                if (!scanner.hasNext()) break;

                String line = scanner.next().trim();

                if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("q")) break;

                if (line.equalsIgnoreCase("clear")) {
                    fibonacciService.clearCache();
                    continue;
                }

                try {
                    int n = Integer.parseInt(line);

                    if (n < 0) throw new NumberFormatException();

                    Instant t0 = Instant.now();
                    BigInteger result = fibonacciService.compute(n);
                    long took = Duration.between(t0, Instant.now()).toMillis();

                    System.out.printf("F(%d) = %s%nВремя вычисления: %d мс%n%n", n, result, took);
                } catch (NumberFormatException ex) {
                    System.out.println("Ошибка: требуется целое число n >= 0.\n");
                } catch (Exception ex) {
                    log.error("Ошибка вычисления", ex);
                    System.out.println("Ошибка: " + ex.getMessage() + "\n");
                }
            }
        }

        System.out.println("Готово!");
    }
}