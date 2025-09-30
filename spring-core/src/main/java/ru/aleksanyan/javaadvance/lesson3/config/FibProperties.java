package ru.aleksanyan.javaadvance.lesson3.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "fib")
public class FibProperties {
    private boolean cacheEnabled = true;
}
