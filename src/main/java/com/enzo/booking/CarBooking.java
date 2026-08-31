package com.enzo.booking;

import com.enzo.booking.enums.BookingStatus;
import com.enzo.car.Car;
import com.enzo.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/*
No setters as there are no top level edition methods.
Attributes that could eventually be editable:
    - 'car'
    - 'startDate'
    - 'endDate'
    - 'price'
*/

public class CarBooking {
    private final UUID id;
    private final User user;
    private final Car car;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final BigDecimal price;
    private BookingStatus status;
    private final LocalDateTime bookedAt;

    public CarBooking(User user, Car car, LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);

        this.id = UUID.randomUUID();
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = calculatePrice(startDate, endDate, car.getRentalPricePerDay());
        status = BookingStatus.ACTIVE;
        bookedAt = LocalDateTime.now();
    }

    // Getters

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    // Booking Status

    public void complete() {
        status = BookingStatus.COMPLETED;
    }

    public void cancel() {
        status = BookingStatus.CANCELLED;
    }

    // Domain logic

    public static void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Booking start date must be today or in the future");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Booking end date must be the same date or after the start date");
        }
    }

    public static BigDecimal calculatePrice(LocalDate startDate, LocalDate endDate, BigDecimal pricePerDay) {
        long rentalDuration;
        if (startDate.isEqual(endDate)) {
            rentalDuration = 1;
        } else {
            rentalDuration = startDate.until(endDate).getDays();
        }

        return BigDecimal.valueOf(rentalDuration).multiply(pricePerDay);
    }

    // Methods override

    @Override
    public String toString() {
        return
                "Id: " + id
                        + ", User: " + user.getName()
                        + ", Car: " + car.getRegNumber()
                        + ", Start Date: " + startDate
                        + ", End Date: " + endDate
                        + ", Total Price: " + price
                        + ", Status: " + status
                        + ", Booked at: " + bookedAt;
    }
}
