package com.parking.src.com.core.history;

import com.parking.src.com.core.report.ReportRecord;
import com.parking.src.com.database.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseParkingHistory implements ParkingHistory {
    String sql = "INSERT INTO parking_history(car_number, enter_time, exit_time, duration_minutes, price)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void saveHistory(String carNumber, LocalDateTime enterTime, LocalDateTime exitTime, long duration, BigDecimal price) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pr = connection.prepareStatement(sql)) {
            pr.setString(1, carNumber);
            pr.setTimestamp(2, Timestamp.valueOf(enterTime));
            pr.setTimestamp(3, Timestamp.valueOf(exitTime));
            pr.setLong(4, duration);
            pr.setBigDecimal(5, price);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("error writing to db", e);
        }
    }

    @Override
    public List<ReportRecord> findAll() {
        List<ReportRecord> records = new ArrayList<>();
        String sql = "SELECT car_number, enter_time, exit_time, duration_minutes, price FROM parking_history";
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String carNumber = resultSet.getString("car_number");
                String enterTime = resultSet.getTimestamp("enter_time").toLocalDateTime().format(formatter);
                String exitTime = resultSet.getTimestamp("exit_time").toLocalDateTime().format(formatter);
                String duration = String.valueOf(resultSet.getLong("duration_minutes"));
                BigDecimal price = resultSet.getBigDecimal("price");
                records.add(new ReportRecord(carNumber, enterTime, exitTime, duration, price));
            }
            return records;
        } catch (SQLException e) {
            throw new RuntimeException("error reading parking history from db" , e);
        }
    }
}
