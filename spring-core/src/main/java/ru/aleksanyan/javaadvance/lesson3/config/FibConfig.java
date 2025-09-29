package ru.aleksanyan.javaadvance.lesson3.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FibProperties.class)
@RequiredArgsConstructor
public class FibConfig {
    private final FibProperties props;

    @Bean
    public CacheManager cacheManager() {
        return props.isCacheEnabled()
                ? new ConcurrentMapCacheManager("fib")
                : new NoOpCacheManager();
    }
}
