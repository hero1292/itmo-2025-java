package ru.aleksanyan.javaadvance.lesson3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class FibApplication {
    public static void main(String[] args) {
        SpringApplication.run(FibApplication.class, args);
    }
}
