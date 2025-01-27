package com.oocl.cultivation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParkingLot {
    private final Integer capacity;
    private Map<ParkingTicket, Car> cars = new HashMap<>();

    public ParkingLot() {
        this(10);
    }

    public ParkingLot(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getAvailableParkingPosition() {
        return cars.size() - capacity;
    }

    public Car fetch(ParkingTicket ticket) {
        Car car = cars.get(ticket);
        cars.remove(ticket);
        return car;
    }

    public void park(Car car, ParkingTicket parkingTicket) {
        cars.put(parkingTicket, car);
    }

    public boolean findCar(ParkingTicket ticket) {
        return Objects.nonNull(cars.get(ticket));
    }
}
