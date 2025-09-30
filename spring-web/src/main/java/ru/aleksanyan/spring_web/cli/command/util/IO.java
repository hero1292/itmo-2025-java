package ru.aleksanyan.spring_web.cli.command.util;

public interface IO {
    String ask(String label);
    long askLong(String label);
    void println(String text);
}
