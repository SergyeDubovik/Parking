package com.parking.src.com.ui;

import com.parking.src.com.core.CarNotFoundException;
import com.parking.src.com.core.Parking;
import com.parking.src.com.core.ParkingImpl;
import com.parking.src.com.pricing.SimplePricingCalculator;
import com.parking.src.com.pricing.WeekendFreeCalculator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class ParkingCLI {
    public static void main(String[] args) throws IOException {
        String fileName;
        if (args.length > 0) {
            fileName = args[0];
        } else {
            fileName = "parking.csv";
        }
        Parking someParking = new ParkingImpl(10, new WeekendFreeCalculator(), fileName);
        someParking.loadData();
        Scanner scanner = new Scanner(System.in);

        runMenu(someParking, scanner);

    }
    private static void runMenu(Parking parking, Scanner sc) {
        while (true) {
            displayMenu();
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    enterCar(parking, sc);
                    break;
                case "2":
                    exitCar(parking, sc);
                    break;
                case "3":
                    showParkingStatus(parking);
                    break;
                case "0":
                    saveToFile(parking);
                    System.out.println("Bye");
                    return;
                default:
                    System.out.println("Wrong input, try again");
            }
        }
    }

    private static void enterCar(Parking parking, Scanner scanner) {
        System.out.println("Please enter the car number...");
        String carNumber = scanner.nextLine();
        try {
            boolean successInput = parking.enter(carNumber);
            if (successInput) {
                System.out.println("Car parked successfully");
            }
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }

    }

    private static void exitCar(Parking parking, Scanner scanner) {
        System.out.println("Please enter the car number to exit...");
        String carNumberOnExit = scanner.nextLine();
        try {
            BigDecimal pay = parking.exit(carNumberOnExit);
            System.out.println("Car " + carNumberOnExit + "has exited. Amount due: $" + pay);
        } catch (CarNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showParkingStatus(Parking parking) {
        boolean[] status = parking.getStatus();
        System.out.println("Slots taken: " + parking.takenSlots() + " of " + status.length);
        for (int i = 0; i < status.length; i++) {
            if (status[i]) {
                System.out.println("[" + (i + 1) + "] - free");
            } else {
                System.out.println("[" + (i + 1) + "] - taken");
            }
        }
        System.out.println();
    }

    private static void saveToFile(Parking parking) {
        try {
            parking.saveData();
            System.out.println("Saving data completed successfully!");
        } catch (IOException e) {
            System.out.println("Failed to save parking data " + e.getMessage());
        }
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("Welcome to car parking");
        System.out.println("1 - Car Enter");
        System.out.println("2 - Car Exit");
        System.out.println("3 - Show Parking Status");
        System.out.println("0 - Close app");
    }
}
