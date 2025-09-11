package ru.aleksanyan.javaadvance.lesson2.converters.temperature;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.aleksanyan.javaadvance.lesson2.domain.temperature.Celsius;
import ru.aleksanyan.javaadvance.lesson2.domain.temperature.Fahrenheit;

@Component
public class CelsiusToFahrenheit implements Converter<Celsius, Fahrenheit> {
    @Override
    public Fahrenheit convert(Celsius source) {
        double f = source.value() * 9.0 / 5.0 + 32.0;
        return new Fahrenheit(f);
    }
}
