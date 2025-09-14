package ru.aleksanyan.javaadvance.lesson3.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.aleksanyan.javaadvance.lesson3.config.FibProperties;

import java.math.BigInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class IterativeFibonacciService implements FibonacciService {
    private final FibProperties props;

    @Override
    @Cacheable(cacheNames = "fib", key = "#n")
    public BigInteger compute(int n) {
        if (n < 0) throw new IllegalArgumentException("n должно быть >= 0");
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;

        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;

        for (int i = 2; i <= n; i++) {
            BigInteger next = a.add(b);
            a = b;
            b = next;
        }

        if (props.isCacheEnabled()) {
            log.info("F({}) вычислен и помещён в кэш", n);
        }

        return b;
    }

    @Override
    @CacheEvict(cacheNames = "fib", allEntries = true)
    public void clearCache() {
        if (props.isCacheEnabled()) {
            log.info("Кэш 'fib' очищен");
        } else {
            log.info("Кэширование отключено, очистка не требуется");
        }
    }
}
