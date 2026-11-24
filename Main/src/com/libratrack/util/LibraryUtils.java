package com.libratrack.util;

/**
 * Provides utility methods for input validation, title formatting,
 * and displaying formatted library headings and separators.
 */
public class LibraryUtils {

    /**
     * Checks whether an input string is valid.
     * A valid input must not be null or empty after trimming whitespace.
     *
     * @param input the string to validate
     * @return true if the input is valid, otherwise false
     */
    public static boolean isValidInput(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Formats a book title by removing leading and trailing whitespace.
     *
     * @param title the title to format
     * @return the trimmed title, or an empty string if the input is invalid
     */
    public static String formatTitle(String title) {
        if (!isValidInput(title)) {
            return "";
        }

        return title.trim();
    }

    /**
     * Prints a horizontal separator line to the console.
     */
    public static void printSeparator() {
        System.out.println("----------------------------------------");
    }

    /**
     * Prints a formatted heading surrounded by separator lines.
     *
     * @param title the heading text to display
     */
    public static void printHeader(String title) {
        printSeparator();
        System.out.println(title);
        printSeparator();
    }
}