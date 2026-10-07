package ug.ac.vu.g13.core;

import java.util.Scanner;

/**
 * @author Kefas
 */
public class InputHelper {
    private static Scanner scanner = new Scanner(System.in);

    public static String readText(String question) {
        while (true) {
            System.out.print(question);
            if (!scanner.hasNextLine()) {
                System.out.println("Input ended. Goodbye.");
                System.exit(0);
            }
            String text = scanner.nextLine().trim();
            if (text.length() > 0) {
                return text.replace("|", "/");
            }
            System.out.println("Please type something.");
        }
    }

    public static int readInt(String question, int min, int max) {
        while (true) {
            String text = readText(question);
            try {
                int number = Integer.parseInt(text);
                if (number >= min && number <= max) {
                    return number;
                }
                System.out.println("Type a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("That is not a whole number.");
            }
        }
    }

    public static double readDouble(String question) {
        while (true) {
            String text = readText(question);
            try {
                return Double.parseDouble(text);
            } catch (NumberFormatException e) {
                System.out.println("That is not a number.");
            }
        }
    }

    public static int menu(String title, String[] options) {
        System.out.println();
        System.out.println("=== " + title + " ===");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.println("0. Back");
        return readInt("Choose: ", 0, options.length);
    }

    public static void printList(String title, java.util.List<? extends Reportable> items) {
        System.out.println("--- " + title + " (" + items.size() + ") ---");
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).reportLine());
        }
    }
}
