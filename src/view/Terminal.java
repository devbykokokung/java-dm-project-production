package src.view;

import java.util.Arrays;
import java.util.function.Consumer;

enum Color {
    red("\033[31m"),
    green("\033[32m"),
    yellow("\033[33m"),
    blue("\033[34m"),
    purple("\033[35m"),
    cyan("\033[36m"),
    white("\033[37m"),
    reset("\033[0m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}

public class Terminal {
    private String welcomeMsg;

    public Terminal(String w) {
        this.welcomeMsg = w;
    }

    public void printWelcomeMsg() {
        clearScreen();
        System.out.println(welcomeMsg);
    }

    public void clearScreen() {
        System.out.println();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void pressAnyKeyToContinue() {
        System.out.print("\n" + color("red", "P") + color("green", "r") + color("yellow", "e") + color("blue", "s")
                + color("purple", "s") + " " + color("cyan", "e") + color("white", "n") + color("red", "t")
                + color("green",
                        "e")
                + color("yellow", "r") + " " + color("blue", "k") + color("purple", "e") + color("cyan", "y")
                + color("white", " ") + color("red", "t") + color("green", "o") + color("yellow", " ")
                + color("blue", "c")
                + color("purple", "o") + color("cyan", "n") + color("white", "t") + color("red", "i")
                + color("green", "n")
                + color("yellow", "u") + color("blue", "e") + "...");

        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    public String color(String c, String s) {
        return Color.valueOf(c) + s + Color.reset;
    }

    public String getWelcomeMsg() {
        return welcomeMsg;
    }

    public void printTable(String[] header, String[][] rows) {
        int padding = 4;
        Integer maxColLength[] = new Integer[header.length];
        for (int i = 0; i < header.length; i++) {
            maxColLength[i] = header[i].length() + padding;
        }
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length; j++) {
                if (rows[i][j].length() > maxColLength[j]) {
                    maxColLength[j] = rows[i][j].length() + padding;
                }
            }
        }

        String line = "+"
                + "-".repeat((header.length - 1) + Arrays.asList(maxColLength).stream().mapToInt(e -> e).sum()) + "+";
        System.out.println(line);
        for (int i = 0; i < header.length; i++) {
            System.out.print("| " + header[i] + " ".repeat(maxColLength[i] - header[i].length() - 1));
        }
        System.out.println("|");
        System.out.println(line);
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length; j++) {
                System.out
                        .print("| " + rows[i][j] + " ".repeat(Math.max(maxColLength[j] - rows[i][j].length() - 1, 0)));
            }
            System.out.println("|");
        }
        System.out.println(line);
    }
}
