package ru.aleksanyan.javaadvance.lesson2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;

import java.util.Set;

@Configuration
public class ConversionConfig {

    @Bean
    public ConversionService conversionService(Set<Converter<?, ?>> converters) {
        FormattingConversionService service = new FormattingConversionService();
        converters.forEach(service::addConverter);
        return service;
    }
}
