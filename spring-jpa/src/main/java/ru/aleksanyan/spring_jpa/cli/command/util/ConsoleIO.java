package ru.aleksanyan.spring_jpa.cli.command.util;

import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class ConsoleIO implements IO {
    private final Scanner in;

    @Override
    public String ask(String label) {
        System.out.print(label);
        return in.nextLine().trim();
    }

    @Override
    public long askLong(String label) {
        while (true) {
            System.out.print(label);
            String s = in.nextLine().trim();

            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    @Override
    public void println(String text) {
        System.out.println(text);
    }
}