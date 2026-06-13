package com.parking.src.com.core.report;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.parking.src.com.core.ParkingImpl.HISTORY_FILE_NAME;

public class HtmlReportGenerator implements ReportGenerator {

    private static final String REPORT_FILE_NAME = "parking-report.html";

    @Override
    public void generateReport() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE_NAME));
             BufferedWriter writer = new BufferedWriter(new FileWriter(REPORT_FILE_NAME))) {
            writer.write("""
                    <!DOCTYPE html><html><head><meta charset="UTF-8"><title>Parking Report</title></head><body>
                    <h1>Parking Report</h1><table border="1"><tr><th>Car Number</th><th>Enter Time</th><th>Exit Time</th>
                    <th>Duration, minutes</th><th>Payment</th></tr>"""); // <tr> means "table row"

            String line;
            List<ReportRecord> records = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String carNumber = parts[0].trim();
                String enterTime = parts[1].trim();
                String exitTime = parts[2].trim();
                String duration = parts[3].trim();
                BigDecimal price = new BigDecimal(parts[4].trim());
                records.add(new ReportRecord(carNumber, enterTime, exitTime, duration, price));
            }
            records.sort(Comparator.comparing(ReportRecord::price));

            for (ReportRecord record : records) {
                writer.write(" <tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>" // <td> means "table data"
                        .formatted(record.carNumber(), record.enterTime(), record.exitTime(), record.duration(), record.price()));
            }
            writer.write("</table></body></html>");
            System.out.println("Report has been written successfully");
        } catch (IOException e) {
            throw new RuntimeException("Error writing report", e);
        }
    }
}
