package com.oocl.cultivation.test;

import com.oocl.cultivation.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ParkingBoyFacts {
    @Test
    void should_get_parking_ticket_when_parking_boy_park_in_parking_lot() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(Collections.singletonList(parkingLot));
        Car car = new Car();

        ParkingTicket parkingTicket = parkingBoy.park(car);
        assertNotNull(parkingTicket);
    }

    @Test
    void should_get_car_when_parking_ticket_is_back_to_parking_boy() {
        Car car = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(Collections.singletonList(parkingLot));
        ParkingTicket parkingTicket = parkingBoy.park(car);

        Car fetchCar = parkingBoy.fetch(parkingTicket);
        assertNotNull(fetchCar);
    }

    @Test
    void should_park_multiple_cars_by_the_parking_Boy() {
        Car car = new Car();
        Car car2 = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(Collections.singletonList(parkingLot));
        ParkingTicket parkingTicket = parkingBoy.park(car);
        ParkingTicket parkingTicket2 = parkingBoy.park(car2);

        Car fetchCar = parkingBoy.fetch(parkingTicket);
        Car fetchCar2 = parkingBoy.fetch(parkingTicket2);

        assertEquals(fetchCar, car);
        assertEquals(fetchCar2, car2);
    }

    @Test
    void should_fetch_right_car_using_corresponding_ticket() {
        Car car = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(Collections.singletonList(parkingLot));
        ParkingTicket parkingTicket = parkingBoy.park(car);

        Car fetchCar = parkingBoy.fetch(parkingTicket);
        assertEquals(fetchCar, car);
    }

    @Test
    void should_fetch_null_car_when_wrong_ticket_is_presented() {
        Car car = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(Collections.singletonList(parkingLot));
        ParkingTicket parkingTicket = parkingBoy.park(car);
        ParkingTicket wrongTicket = new ParkingTicket();


        Car fetchCar = parkingBoy.fetch(wrongTicket);
        assertNull(fetchCar);
    }

    @Test
    void should_fetch_null_car_when_ticket_was_already_used() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(Collections.singletonList(parkingLot));
        Car car = new Car();

        ParkingTicket parkingTicket = parkingBoy.park(car);
        Car fetchCar = parkingBoy.fetch(parkingTicket);
        Car fetchCar2 = parkingBoy.fetch(parkingTicket);
        assertNull(fetchCar2);
    }

    @Test
    void should_not_park_in_the_parking_lot_when_parking_lot_is_full() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(Collections.singletonList(parkingLot));

        IntStream.rangeClosed(0, 10).forEach(value -> {
            Car car = new Car();
            ParkingTicket parkingTicket = parkingBoy.park(car);
        });
        Car car = new Car();
        ParkingTicket parkingTicket = parkingBoy.park(car);
        assertNull(parkingTicket);
    }

    @Test
    void should_return_msg_unrecognized_parking_ticket_when_invalid_ticket_is_given_to_parking_boy() {
        String expectedString = "Unrecognized parking ticket.";
        Car car = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(Collections.singletonList(parkingLot));
        ParkingTicket parkingTicket = parkingBoy.park(car);
        Car fetchCar = parkingBoy.fetch(null);

        String actualString = parkingBoy.getMessage();
        assertEquals(actualString, expectedString);
    }

    @Test
    void should_return_provide_ticket_message_when_null_ticket_is_given_to_parking_boy() {
        String expectedString = "Please provide your parking ticket.";
        Car car = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(Collections.singletonList(parkingLot));
        ParkingTicket parkingTicket = parkingBoy.park(car);
        Car fetchCar = parkingBoy.fetch(new ParkingTicket());

        String actualString = parkingBoy.getMessage();
        assertEquals(actualString, expectedString);
    }

    @Test
    void should_return_not_ebough_parking_position_message_when_parking_availability_is_full() {
        String expectedString = "Not enough position.";
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(Arrays.asList(parkingLot));
        IntStream.rangeClosed(0, 10).forEach(value -> {
            Car car = new Car();
            ParkingTicket parkingTicket = parkingBoy.park(car);
        });
        Car car = new Car();
        ParkingTicket parkingTicket = parkingBoy.park(car);

        String actualString = parkingBoy.getMessage();
        assertEquals(actualString, expectedString);
    }

    @Test
    void should_park_to_parking_2_when_parking_lot_1_is_full() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(Arrays.asList(parkingLot, parkingLot2));
        IntStream.rangeClosed(0, 10).forEach(value -> {
            Car car = new Car();
            ParkingTicket parkingTicket = parkingBoy.park(car);
        });
        Car car = new Car();
        ParkingTicket ticket = parkingBoy.park(car);
        Car fetchCar = parkingBoy.fetch(ticket);
        assertEquals(fetchCar, car);
    }

    @Test
    void should_park_car_to_more_spacious_parking_lot_when_smart_parking_boy_park_the_car() {
        ParkingLot currentParkingLot = new ParkingLot();
        ParkingLot expectedParkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(Arrays.asList(currentParkingLot, expectedParkingLot));
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingBoy);
        IntStream.rangeClosed(0, 4).forEach(value -> {
            Car car = new Car();
            ParkingTicket parkingTicket = parkingBoy.park(car);
        });

        IntStream.rangeClosed(0, 4).forEach(value -> {
            Car car = new Car();
            ParkingTicket parkingTicket = smartParkingBoy.park(car);
        });

        Car car = new Car();
        ParkingTicket parkingTicket = smartParkingBoy.park(car);
        ParkingLot actualParkingLot = smartParkingBoy.getCurrentParkingLot(parkingTicket);
        assertEquals(actualParkingLot, currentParkingLot);

        car = new Car();
        parkingTicket = smartParkingBoy.park(car);
        actualParkingLot = smartParkingBoy.getCurrentParkingLot(parkingTicket);
        assertEquals(actualParkingLot, expectedParkingLot);
    }

    @Test
    void should_park_car_to_larger_available_position_rate_when_smart_parking_boy_park_the_car() {
        ParkingLot currentParkingLot = new ParkingLot();
        ParkingLot expectedParkingLot = new ParkingLot();
        ParkingLot anotherParkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(Arrays.asList(currentParkingLot, expectedParkingLot, anotherParkingLot));
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingBoy);
        IntStream.rangeClosed(0, 4).forEach(value -> {
            Car car = new Car();
            ParkingTicket parkingTicket = parkingBoy.park(car);
        });

        IntStream.rangeClosed(0, 4).forEach(value -> {
            Car car = new Car();
            ParkingTicket parkingTicket = smartParkingBoy.park(car);
        });

        Car car = new Car();

        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(smartParkingBoy);
        ParkingTicket parkingTicket = superSmartParkingBoy.park(car);
        ParkingLot actualParkingLot = superSmartParkingBoy.getCurrentParkingLot(parkingTicket);
        assertEquals(actualParkingLot, anotherParkingLot);
    }
}
