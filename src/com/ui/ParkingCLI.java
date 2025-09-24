package com.parking.src.com.ui;

import com.parking.src.com.core.Parking;
import com.parking.src.com.core.ParkingImpl;
import com.parking.src.com.pricing.SimplePricingCalculator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class ParkingCLI {
    public static void main(String[] args) throws IOException {
        Parking someParking = new ParkingImpl(10, new SimplePricingCalculator());
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
                    boolean[] status = parking.getStatus();
                    showParkingStatus(status);
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
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showParkingStatus(boolean[] status) {
        int taken = 0;
        int totalParkingSlots = status.length;
        for (boolean slot : status) {
            if (!slot) {
                taken++;
            }
        }
        System.out.println("Slots taken: " + taken + " of " + totalParkingSlots);
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
