package ru.aleksanyan.javaadvance.lesson2.core;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversionFacade {
    private final ConversionService conversionService;

    public <T> T convert(Object source, Class<T> targetType) {
        if (!conversionService.canConvert(source.getClass(), targetType)) {
            throw new IllegalArgumentException(
                    "No converter registered from %s to %s"
                            .formatted(source.getClass().getSimpleName(), targetType.getSimpleName())
            );
        }
        return conversionService.convert(source, targetType);
    }
}
