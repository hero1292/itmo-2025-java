package ru.aleksanyan.javaadvance.lesson2.demo;

import lombok.RequiredArgsConstructor;
import ru.aleksanyan.javaadvance.lesson2.core.ConversionFacade;
import ru.aleksanyan.javaadvance.lesson2.domain.temperature.Celsius;
import ru.aleksanyan.javaadvance.lesson2.domain.temperature.Fahrenheit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DemoRunner implements CommandLineRunner {

    private final ConversionFacade conversion;

    @Override
    public void run(String... args) {
        Celsius c = new Celsius(25.0);
        Fahrenheit f = conversion.convert(c, Fahrenheit.class);
        System.out.println("25°C -> " + f.value() + "°F");

        Fahrenheit f2 = new Fahrenheit(77.0);
        Celsius c2 = conversion.convert(f2, Celsius.class);
        System.out.println("77°F -> " + c2.value() + "°C");
    }
}
