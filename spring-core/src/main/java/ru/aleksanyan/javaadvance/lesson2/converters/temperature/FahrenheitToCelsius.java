package ru.aleksanyan.javaadvance.lesson2.converters.temperature;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.aleksanyan.javaadvance.lesson2.domain.temperature.Celsius;
import ru.aleksanyan.javaadvance.lesson2.domain.temperature.Fahrenheit;

@Component
public class FahrenheitToCelsius implements Converter<Fahrenheit, Celsius> {
    @Override
    public Celsius convert(Fahrenheit source) {
        double c = (source.value() - 32.0) * 5.0 / 9.0;
        return new Celsius(c);
    }
}
