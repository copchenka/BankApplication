package ru.levelup.bank.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleMenu {
    private static final BufferedReader CONSOLE_READER = new BufferedReader(new InputStreamReader(System.in));

    public static void printGeneralMenu() {
        System.out.println();
        System.out.println("Меню");
        System.out.println("1. Список всех компаний");
        System.out.println("2. Список всех клиентов");
        System.out.println("3. Получить информацию о клиенте");
        System.out.println("4. Привязать клиента к компании");
        System.out.println("0. Выход");
    }

    private static String readString(String message) {
        try {
            System.out.println(message);
            return CONSOLE_READER.readLine(); //не закрываем поток тк все внутри в system.in мы не сами прописали открытие
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static int readInt(String message) {
        try {
            return Integer.parseInt(readString(message));
        } catch (NumberFormatException exc) {
            throw new RuntimeException(exc);
        }

    }
}
