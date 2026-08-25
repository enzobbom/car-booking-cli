package com.enzo.booking;

import java.util.Arrays;

public class CarBookingDao {
    private static final CarBooking[] bookings = new CarBooking[100];
    private static int nextBookingIndex = 0;

    public void saveBooking(CarBooking booking) {
        if (nextBookingIndex >= bookings.length) {
            throw new IllegalStateException("System reached its booking limit. Not possible to create a new booking");
        }

        bookings[nextBookingIndex++] = booking;
    }

    public CarBooking[] getBookings() {
        if (nextBookingIndex == 0) { return new CarBooking[0]; }
        return Arrays.copyOfRange(bookings, 0, nextBookingIndex);
    }
}
