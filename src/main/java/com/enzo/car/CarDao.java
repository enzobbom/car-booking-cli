package com.enzo.car;

import com.enzo.car.enums.Brand;

import java.math.BigDecimal;

public class CarDao {
    private static final Car[] cars;
    static {
        cars = new Car[]{
                new Car("211-D-45892", new BigDecimal("44.53"), Brand.TOYOTA, false),
                new Car("182-C-1245", new BigDecimal("50.97"), Brand.TOYOTA, false),
                new Car("141-G-9932", new BigDecimal("74.29"), Brand.AUDI, false),
                new Car("222-KY-412", new BigDecimal("93.45"), Brand.TESLA, true),
                new Car("161-WW-8391", new BigDecimal("101.33"), Brand.AUDI, false),
                new Car("192-LK-15602", new BigDecimal("110.13"), Brand.TESLA, true),
                new Car("12-LH-4512", new BigDecimal("127.38"), Brand.MERCEDES, true),
                new Car("231-SO-784", new BigDecimal("150.44"), Brand.TESLA, true),
                new Car("152-WH-3310", new BigDecimal("152.19"), Brand.MERCEDES, true),
        };
    }

    public Car[] getCars() {
        return cars;
    }
}
