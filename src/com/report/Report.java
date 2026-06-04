package com.parking.src.com.report;

import java.io.*;

public class Report {
    private static final String HISTORY_FILE_NAME = "parking-history.csv";
    private static final String REPORT_FILE_NAME = "parking-report.html";

    public void generateReport () {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE_NAME));
        BufferedWriter writer = new BufferedWriter(new FileWriter(REPORT_FILE_NAME))) {
            writer.write("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta charset="UTF-8">
                        <title>Parking Report</title>
                    </head>
                    <body>
                        <h1>Parking Report</h1>
                        <table border="1">
                            <tr>
                                <th>Car Number</th>
                                <th>Enter Time</th>
                                <th>Exit Time</th>
                                <th>Duration, minutes</th>
                                <th>Payment</th>
                            </tr>
                    """);
            // <tr> means "table row"

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String carNumber = parts[0].trim();
                String enterTime = parts[1].trim();
                String exitTime = parts[2].trim();
                String duration = parts[3].trim();
                String price = parts[4].trim();
                writer.write("""
                        <tr>
                        <td>%s</td>
                        <td>%s</td>
                        <td>%s</td>
                        <td>%s</td>
                        <td>%s</td>
                        """.formatted(carNumber, enterTime, exitTime, duration, price));
                // <td> means "table data"
            }
            writer.write("""
                    </table>
                    </body>
                    </html>
                    """);
            System.out.println("Report has been written successfully");
        } catch (IOException e) {
            throw new RuntimeException("Error writing report", e);
        }
    }
}
