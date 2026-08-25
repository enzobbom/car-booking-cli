package com.enzo.booking;

import com.enzo.booking.enums.BookingStatus;
import com.enzo.car.Car;
import com.enzo.car.CarService;
import com.enzo.user.User;
import com.enzo.user.UserService;

import java.time.LocalDate;
import java.util.UUID;

public class CarBookingService {
    private final CarService carService = new CarService();
    private final UserService userService = new UserService();
    private final CarBookingDao carBookingDao = new CarBookingDao();

    private static final String BOOKING_NOT_FOUND_MSG = "Booking not found";
    private static final String CAR_UNAVAILABLE_MSG = "Car is unavailable";

    public User[] getUsers() {
        return userService.getUsers();
    }

    public CarBooking bookCar(UUID userId, UUID carId, LocalDate startDate, LocalDate endDate) {
        User desiredUser = userService.getUser(userId);
        Car desiredCar = carService.getCar(carId);
        CarBooking.validateDates(startDate, endDate);

        Car[] availableCars = getAvailableCars(startDate, endDate);

        if (availableCars.length == 0) {
            throw new IllegalStateException(CAR_UNAVAILABLE_MSG);
        }

        boolean isCarAvailable = false;
        for (Car car : availableCars) {
            if (car == desiredCar) {
                isCarAvailable = true;
                break;
            }
        }

        if (!isCarAvailable) {
            throw new IllegalStateException(CAR_UNAVAILABLE_MSG);
        }

        CarBooking newBooking = new CarBooking(
                desiredUser,
                desiredCar,
                startDate,
                endDate
        );

        carBookingDao.saveBooking(newBooking);

        return newBooking;
    }

    public CarBooking[] getBookings() {
        return carBookingDao.getBookings();
    }

    public void deleteBooking(UUID bookingId) {
        getBooking(bookingId).cancel();
    }

    public Car[] getUserBookedCars(UUID userId) {
        User desiredUser = userService.getUser(userId);

        CarBooking[] bookings = carBookingDao.getBookings();
        int bookingsCount = bookings.length;

        if (bookingsCount == 0) { return new Car[0]; }

        int userBookingsCount = 0;
        for (CarBooking booking : bookings) {
            if (booking.getUser() == desiredUser) {
                userBookingsCount++;
            }
        }

        if (userBookingsCount == 0) { return new Car[0]; }

        Car[] userCars = new Car[userBookingsCount];
        int userCarsCounter = 0;
        for (CarBooking booking : bookings) {
            if (booking.getUser() == desiredUser) {
                userCars[userCarsCounter++] = booking.getCar();
            }
        }

        return userCars;
    }

    public Car[] getAvailableCars() {
        return getAvailableCars(LocalDate.now(), LocalDate.now());
    }

    public Car[] getAvailableElectricCars() {
        Car[] availableCars = getAvailableCars();
        if (availableCars.length == 0) { return new Car[0]; }

        return carService.getElectricCars(availableCars);
    }

    public Car[] getAvailableCars(LocalDate startDate, LocalDate endDate) {
        CarBooking[] bookings = getBookings();
        Car[] cars = carService.getCars();

        if (bookings.length == 0) { return cars; }

        int totalCarsCount = cars.length;

        boolean[] unavailableCarsMap = new boolean[totalCarsCount];
        int unavailableCarsCount = 0;
        int bookedCarIdx = 0;
        for (CarBooking booking : bookings) {
            if (hasBookingDateConflict(booking, startDate, endDate) && booking.getStatus() == BookingStatus.ACTIVE) {
                Car bookedCar = booking.getCar();

                for (int cIdx = 0; cIdx < totalCarsCount; cIdx++) {
                    if (bookedCar == cars[cIdx]) {
                        bookedCarIdx = cIdx;
                        break;
                    }
                }

                if (!unavailableCarsMap[bookedCarIdx]) {
                    unavailableCarsMap[bookedCarIdx] = true;
                    unavailableCarsCount++;

                    if (unavailableCarsCount == totalCarsCount) {
                        return new Car[0];
                    }
                }
            }
        }

        if (unavailableCarsCount == 0) { return cars; }

        Car[] availableCars = new Car[totalCarsCount - unavailableCarsCount];
        int availableCarsCounter = 0;
        for (int cIdx = 0; cIdx < totalCarsCount; cIdx++) {
            if (!unavailableCarsMap[cIdx]) {
                availableCars[availableCarsCounter++] = cars[cIdx];
            }
        }

        return availableCars;
    }

    private CarBooking getBooking(UUID bookingId) {
        CarBooking[] bookings = carBookingDao.getBookings();
        int bookingsCount = bookings.length;

        if (bookingsCount == 0) {
            throw new IllegalArgumentException(BOOKING_NOT_FOUND_MSG);
        }

        CarBooking desiredBooking = null;
        for (CarBooking booking : bookings) {
            if (bookingId.equals(booking.getId())) {
                desiredBooking = booking;
                break;
            }
        }

        if (desiredBooking == null) {
            throw new IllegalArgumentException(BOOKING_NOT_FOUND_MSG);
        }

        return desiredBooking;
    }

    private static boolean hasBookingDateConflict(CarBooking booking, LocalDate desiredStartDate, LocalDate desiredEndDate) {
        if (booking.getStartDate().isAfter(desiredEndDate)) {
            return false;
        }
        return !booking.getEndDate().isBefore(desiredStartDate);
    }
}
