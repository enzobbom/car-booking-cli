package com.enzo.car;

import com.enzo.car.enums.Brand;

import java.math.BigDecimal;
import java.util.UUID;

/*
No setters as there are no top level edition methods.
Attributes that could eventually be editable:
    - 'rentalPricePerDay'
    - 'regNumber'
*/

public class Car {
    private final UUID id;
    private final String regNumber;
    private final BigDecimal rentalPricePerDay;
    private final Brand brand;
    private final boolean isElectric;

    public Car(String regNumber, BigDecimal rentalPricePerDay, Brand brand, boolean isElectric) {
        this.id = UUID.randomUUID();
        this.regNumber = regNumber;
        this.rentalPricePerDay = rentalPricePerDay;
        this.brand = brand;
        this.isElectric = isElectric;
    }

    public UUID getId() {
        return id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public BigDecimal getRentalPricePerDay() {
        return rentalPricePerDay;
    }

    public Brand getBrand() {
        return brand;
    }

    public boolean isElectric() {
        return isElectric;
    }

    @Override
    public String toString() {
        return
                "Id: " + id
                        + ", Registration No.: " + regNumber
                        + ", Price per Day: " + rentalPricePerDay
                        + ", Brand: " + brand
                        + ", Electric: " + isElectric;
    }
}
