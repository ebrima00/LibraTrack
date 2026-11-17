package com.libratrack.util;

public class LibraryUtils {

    public static boolean isValidInput(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static String formatTitle(String title) {
        if (!isValidInput(title)) {
            return "";
        }

        return title.trim();
    }

    public static void printSeparator() {
        System.out.println("----------------------------------------");
    }

    public static void printHeader(String title) {
        printSeparator();
        System.out.println(title);
        printSeparator();
    }
}