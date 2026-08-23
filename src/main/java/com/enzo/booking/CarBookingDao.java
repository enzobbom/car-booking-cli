package com.enzo.booking;

import java.util.Arrays;

public class CarBookingDao {
    private static final CarBooking[] BOOKINGS = new CarBooking[100];
    private static int nextBookingIndex = 0;

    public void saveBooking(CarBooking booking) {
        if (nextBookingIndex >= BOOKINGS.length) {
            throw new IllegalStateException("Too many car bookings");
        }

        BOOKINGS[nextBookingIndex++] = booking;
    }

    public CarBooking[] getBookings() {
        if (nextBookingIndex == 0) { return new CarBooking[0]; }
        return Arrays.copyOfRange(BOOKINGS, 0, nextBookingIndex);
    }
}
