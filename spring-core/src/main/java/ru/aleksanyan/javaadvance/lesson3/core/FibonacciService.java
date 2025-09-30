package ru.aleksanyan.javaadvance.lesson3.core;

import java.math.BigInteger;

public interface FibonacciService {
    BigInteger compute(int n);
    void clearCache();
}
