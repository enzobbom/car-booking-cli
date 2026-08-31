package com.enzo;

import com.enzo.booking.CarBooking;
import com.enzo.booking.CarBookingService;
import com.enzo.car.Car;
import com.enzo.user.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class CarBookingCLI {
    private static final CarBookingService carBookingService = new CarBookingService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            int choice = displayMenu();

            try {
                switch (choice) {
                    case 1 -> bookCar();
                    case 2 -> deleteBooking();
                    case 3 -> viewAllUserBookedCars();
                    case 4 -> viewAllBookings();
                    case 5 -> viewAvailableCars();
                    case 6 -> viewAvailableElectricCars();
                    case 7 -> viewAllUsers();
                    case 8 -> running = false;
                    default -> throw new IllegalStateException("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }

        scanner.close();
    }

    // User input

    private static int displayMenu() {
        System.out.println("Choose one of the options below:");
        System.out.println("1 - Book car");
        System.out.println("2 - Delete booking");
        System.out.println("3 - View All User Booked Cars");
        System.out.println("4 - View All Bookings");
        System.out.println("5 - View Available Cars");
        System.out.println("6 - View Available Electric Cars");
        System.out.println("7 - View All Users");
        System.out.println("8 - Exit");
        System.out.println();

        int choice = scanner.nextInt();
        scanner.nextLine();

        return choice;
    }

    private static UUID promptId(String promptMsg, String errorMsg) {
        System.out.println(promptMsg);
        String idStr = scanner.nextLine();

        try {
            return UUID.fromString(idStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    private static LocalDate promptDate(String promptMsg) {
        System.out.println(promptMsg);
        String dateStr = scanner.nextLine();

        try {
            return LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid Date");
        }
    }

    // Service

    private static void bookCar() {
        UUID userId = promptId("Please insert an User ID:", "Invalid User ID");
        UUID carId = promptId("Please insert a Car ID:", "Invalid Car ID");
        LocalDate startDate = promptDate("Please insert the starting date (dd-mm-yyyy):");
        LocalDate endDate = promptDate("Please insert the ending date (dd-mm-yyyy):");

        CarBooking booking = carBookingService.bookCar(
                userId,
                carId,
                startDate,
                endDate
        );

        displayResult(booking);
    }

    private static void deleteBooking() {
        UUID userId = promptId("Please insert a Booking ID:", "Invalid Booking ID");

        carBookingService.deleteBooking(userId);

        System.out.println("Booking deleted");
        System.out.println();
    }

    private static void viewAllUserBookedCars() {
        UUID userId = promptId("Please insert an User ID:", "Invalid User ID");

        Car[] userBookedCars = carBookingService.getUserBookedCars(userId);

        displayResult(userBookedCars);
    }

    private static void viewAllBookings() {
        CarBooking[] bookings = carBookingService.getBookings();

        displayResult(bookings);
    }

    private static void viewAvailableCars() {
        Car[] availableCars = carBookingService.getAvailableCars();

        displayResult(availableCars);
    }

    private static void viewAvailableElectricCars() {
        Car[] electricCars = carBookingService.getAvailableElectricCars();

        displayResult(electricCars);
    }

    private static void viewAllUsers() {
        User[] users = carBookingService.getUsers();

        displayResult(users);
    }

    private static void displayResult(Object[] entities) {
        for (Object o : entities) {
            System.out.println(o);
        }

        System.out.println();
    }

    private static void displayResult(Object entity) {
        System.out.println(entity);
        System.out.println();
    }
}
