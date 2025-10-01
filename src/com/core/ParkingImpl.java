package com.parking.src.com.core;

import com.parking.src.com.model.ParkingRecord;
import com.parking.src.com.pricing.PricingCalculator;

import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParkingImpl implements Parking {
    private final int size;
    private final boolean[] isFree;
    private final PricingCalculator calculator;
    private final Map<String, ParkingRecord> visitors = new HashMap<>();
    private final String fileName;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ParkingImpl(int size, PricingCalculator calculator, String fileName) {
        this.size = size;
        isFree = new boolean[size];
        Arrays.fill(isFree, true);
        this.calculator = calculator;
        this.fileName = fileName;
    }

    public ParkingImpl(int size, PricingCalculator calculator) {
        this(size,calculator, "parking.csv");
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
        LocalDateTime enterTime = record.getEnterTime();
        Duration duration = Duration.between(enterTime, now);
        isFree[record.getSlot()] = true;
        visitors.remove(carNumber);
        return calculator.calculate(duration);
    }

    @Override
    public void saveData() throws IOException {
        if (visitors.isEmpty()) {
            System.out.println("The parking is empty. No data to save.");
            return;
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(fileName))) {
            for (Map.Entry<String, ParkingRecord> entry : visitors.entrySet()) {
                ParkingRecord pr = entry.getValue();
                String formattedDate = pr.getEnterTime().format(formatter);
                String line = entry.getKey() + "," + formattedDate + "," + pr.getSlot() + "\n";
                bufferedWriter.write(line);
            }
        }
    }

    @Override
    public void loadData() throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] partsOfData = line.split(",");
                if (partsOfData.length < 3) {
                    continue;
                }
                String carNumber = partsOfData[0].trim();
                String date = partsOfData[1].trim();
                LocalDateTime localDateTime = tryParseDateTime(date);
                if (localDateTime == null) {
                    continue;
                }
                Integer slot = tryParseSlot(partsOfData);
                if (slot == null) {
                    continue;
                }
                ParkingRecord value = new ParkingRecord(slot, localDateTime);
                visitors.put(carNumber, value);
                isFree[slot] = false;
            }
        }
    }

    @Override
    public boolean[] getStatus() {
        return isFree;
    }

    @Override
    public int takenSlots() {
        return visitors.size();
    }


    private static Integer tryParseSlot(String[] partsOfData) {
        int slot;
        try {
            slot = Integer.parseInt(partsOfData[2].trim());
            if (slot < 0) {
                System.out.println("Slot number shouldn't be less than zero. Found: " + slot);
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Couldn't parse slot, slot should be a number " + e);
            return null;
        }
        return slot;
    }

    private LocalDateTime tryParseDateTime(String date) {
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException exception) {
            System.out.println("Could not parse date, please check format " + exception);
            return null;
        }
        return localDateTime;
    }
}

