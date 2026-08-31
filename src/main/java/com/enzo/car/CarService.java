package com.enzo.car;

import java.util.UUID;

public class CarService {
    private final CarDao carDao = new CarDao();

    private static final String CAR_NOT_FOUND_MSG = "Car not found";

    public Car[] getCars() {
        return carDao.getCars();
    }

    public Car getCar(UUID carId) {
        Car[] cars = getCars();

        if (cars.length == 0) {
            throw new IllegalArgumentException(CAR_NOT_FOUND_MSG);
        }

        Car desiredCar = null;
        for (Car car : cars) {
            if (carId.equals(car.getId())) {
                desiredCar = car;
                break;
            }
        }

        if (desiredCar == null) {
            throw new IllegalArgumentException(CAR_NOT_FOUND_MSG);
        }

        return desiredCar;
    }

    public Car[] getElectricCars() {
        return filterElectricCars(getCars());
    }

    public Car[] filterElectricCars(Car[] cars) {
        if (cars.length == 0) { return new Car[0]; }

        int electricCarsCount = 0;
        for (Car car : cars) {
            if (car.isElectric()) {
                electricCarsCount++;
            }
        }

        if (electricCarsCount == 0) {
            return new Car[0];
        } else if (electricCarsCount == cars.length) {
            return cars;
        }

        Car[] electricCars = new Car[electricCarsCount];
        int electricCarsCounter = 0;
        for (Car car : cars) {
            if (car.isElectric()) {
                electricCars[electricCarsCounter++] = car;

                if (electricCarsCounter == electricCarsCount + 1) {
                    break;
                }
            }
        }

        return electricCars;
    }
}
