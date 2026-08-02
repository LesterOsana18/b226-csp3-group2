package com.joysistvi.univenrollmentapp.utils;

// Utility Class
// Provides reusable methods for formatting console tables
public final class TableFormatter {

    // Prevent instantiation
    private TableFormatter() {
    }

    // Prints a formatted table
    public static void printTable(
            String[] headers,
            String[][] rows) {

        if (rows == null || rows.length == 0) {

            printNoRecordsFound();

            return;

        }

        int[] columnWidths = calculateColumnWidths(headers, rows);

        printBorder(columnWidths);

        printRow(headers, columnWidths);

        printBorder(columnWidths);

        for (String[] row : rows) {

            printRow(row, columnWidths);

        }

        printBorder(columnWidths);

        printTotalRecords(rows.length);

    }

    // Prints a single row
    private static void printRow(
            String[] values,
            int[] widths) {

        System.out.print("|");

        for (int i = 0; i < values.length; i++) {

            System.out.printf(
                    " %-" + widths[i] + "s |",
                    values[i]);

        }

        System.out.println();

    }

    // Prints the table border
    private static void printBorder(
            int[] widths) {

        System.out.print("+");

        for (int width : widths) {

            for (int i = 0; i < width + 2; i++) {

                System.out.print("-");

            }

            System.out.print("+");

        }

        System.out.println();

    }

    // Calculates the width of every column
    private static int[] calculateColumnWidths(
            String[] headers,
            String[][] rows) {

        int[] widths = new int[headers.length];

        for (int i = 0; i < headers.length; i++) {

            widths[i] = headers[i].length();

        }

        for (String[] row : rows) {

            for (int i = 0; i < row.length; i++) {

                widths[i] = Math.max(
                        widths[i],
                        row[i].length());

            }

        }

        return widths;

    }

    // Prints a simple divider line
    public static void printDivider() {
        printDivider(80);
    }

    // Specifies the length of the divider line
    public static void printDivider(int length) {
        System.out.println("-".repeat(length));
    }

    // Prints total number of records
    public static void printTotalRecords(
            int total) {

        System.out.println("\nTotal Records: " + total);

    }

    // Prints no records found
    public static void printNoRecordsFound() {

        MessagePrinter.info("No records found.");

    }

}