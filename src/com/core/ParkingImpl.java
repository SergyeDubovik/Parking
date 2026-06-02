package com.parking.src.com.core;

import com.parking.src.com.database.DatabaseConnection;
import com.parking.src.com.model.ParkingRecord;
import com.parking.src.com.pricing.PricingCalculator;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParkingImpl implements PersistableParking {
    private final int size;
    private final boolean[] isFree;
    private final PricingCalculator calculator;
    private final Map<String, ParkingRecord> visitors = new HashMap<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ParkingImpl(int size, PricingCalculator calculator) {
        this.size = size;
        isFree = new boolean[size];
        Arrays.fill(isFree, true);
        this.calculator = calculator;
    }

    @Override
    public boolean enter(String carNumber) {
        if (visitors.containsKey(carNumber)) {
            throw new RuntimeException("Car " + carNumber + " is already parked");
        }
        for (int i = 0; i < isFree.length; i++) {
            if (isFree[i]) {
                isFree[i] = false;
                LocalDateTime enterCar = LocalDateTime.now();
                ParkingRecord record = new ParkingRecord(i, enterCar);
                visitors.put(carNumber, record);
                return true;
            }
        }
        System.out.println("Sorry, parking is already full. " + visitors.size() + "/" + size + " slots taken");
        return false;
    }


    @Override
    public BigDecimal exit(String carNumber) {
        if (!visitors.containsKey(carNumber)) {
            throw new CarNotFoundException("Car not found. Please verify data and try again.");
        }
        LocalDateTime now = LocalDateTime.now();
        ParkingRecord record = visitors.get(carNumber);
        LocalDateTime enterTime = record.enterTime();

        BigDecimal price = calculator.calculate(enterTime, now);
        deleteFromDatabase(carNumber);

        isFree[record.slot()] = true;
        visitors.remove(carNumber);
        return price;
    }

    @Override
    public void saveData() throws SQLException {
        String deleteSql = "DELETE FROM parking";

        String insertSql = """
                INSERT INTO parking (car_number, enter_time, slot)
                VALUES (?, ?, ?)
                """;
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                 PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                deleteStatement.executeUpdate();

                for (Map.Entry<String, ParkingRecord> entry : visitors.entrySet()) {
                    ParkingRecord record = entry.getValue();

                    insertStatement.setString(1, entry.getKey());
                    insertStatement.setTimestamp(2, Timestamp.valueOf(record.enterTime()));
                    insertStatement.setInt(3, record.slot());

                    insertStatement.executeUpdate();
                }
                connection.commit();
                System.out.println("Data saved to database");
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException exception) {
            throw new SQLException("Error saving to database", exception);
        }

    }

    @Override
    public void loadData() {
        String sql = "SELECT car_number, enter_time, slot FROM parking";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String carNumber = rs.getString("car_number");
                LocalDateTime enterTime = rs.getTimestamp("enter_time").toLocalDateTime();
                int slot = rs.getInt("slot");

                if (slot < 0 || slot >= size) {
                    System.out.println("Invalid slot in database: " + slot);
                    continue;
                }
                ParkingRecord parkingRecord = new ParkingRecord(slot, enterTime);
                visitors.put(carNumber, parkingRecord);
                isFree[slot] = false;
            }
            System.out.println("Data loaded from database successfully");
        } catch (SQLException exception) {
            throw new RuntimeException("Error loading data from database", exception);
        }
    }

    public Optional<Integer> findCar(String carNumber) {
        ParkingRecord record = visitors.get(carNumber);
        if (record != null) {
            return Optional.of(record.slot() + 1);
        }
        return Optional.empty();
    }

    @Override
    public boolean[] getStatus() {
        return isFree;
    }

    @Override
    public int takenSlots() {
        return visitors.size();
    }

    private void deleteFromDatabase(String carNumber) {
        String sql = "DELETE FROM parking WHERE car_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, carNumber);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting from database", e);
        }
    }
}

