
package com.university.mockclient;

import java.util.regex.Pattern;
import java.util.regex.Matcher;



public class PrettyUtil {

    public static final String RESET = "\u001B[0m";

    public static final String BOLD = "\u001B[1m";

    public static final String BLUE = "\u001B[34m";

    public static final String GREEN = "\u001B[32m";

    public static final String RED = "\u001B[31m";



    // Console formatting methods

    public static void printBox(String title, int width) {

        System.out.println(BLUE + "╔" + "═".repeat(width - 2) + "╗" + RESET);

        System.out.println(BLUE + "║" + BOLD + centerString(title, width - 2) + RESET + BLUE + "║" + RESET);

        System.out.println(BLUE + "╠" + "═".repeat(width - 2) + "╣" + RESET);

    }



    public static void printMenuItem(int number, String description) {

        System.out.printf(BLUE + "║" + RESET + " %-2d. %-" + (36) + "s " + BLUE + "║\n" + RESET, number, description);

    }



    public static void printBoxBottom(int width) {

        System.out.println(BLUE + "╚" + "═".repeat(width - 2) + "╝" + RESET);

    }



    public static String centerString(String s, int width) {

        return String.format("%-" + width + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));

    }



    public static void printSuccess(String message) {

        System.out.println(GREEN + "✓ " + message + RESET);

    }



    public static void printError(String message) {

        System.out.println(RED + "✗ " + message + RESET);

    }



    // JSON formatting class

    public static class JsonFormatter {

        private static final String INDENT = "    ";

        private static final Pattern JSON_REGEX = Pattern.compile("(\"(?:\\\\.|[^\"])*\")|([\\[{\\]},:])");



        public static String prettyPrintJson(String json) {

            StringBuilder prettyJson = new StringBuilder();

            int indentLevel = 0;

            boolean beginLine = true;

            Matcher matcher = JSON_REGEX.matcher(json);



            while (matcher.find()) {

                String part = matcher.group();

                if ("{".equals(part) || "[".equals(part)) {

                    if (!beginLine) prettyJson.append("\n");

                    prettyJson.append(INDENT.repeat(indentLevel)).append(part).append("\n");

                    indentLevel++;

                    beginLine = true;

                } else if ("}".equals(part) || "]".equals(part)) {

                    indentLevel--;

                    if (!beginLine) prettyJson.append("\n");

                    prettyJson.append(INDENT.repeat(indentLevel)).append(part);

                    beginLine = false;

                } else if (",".equals(part)) {

                    prettyJson.append(part).append("\n");

                    beginLine = true;

                } else if (":".equals(part)) {

                    prettyJson.append(part).append(" ");

                    beginLine = false;

                } else {

                    if (beginLine) prettyJson.append(INDENT.repeat(indentLevel));

                    prettyJson.append(part);

                    beginLine = false;

                }

            }

            return prettyJson.toString();

       }

    }

}