package com.oocl.cultivation;

import java.util.List;
import java.util.Objects;

public class SuperSmartParkingBoy extends SmartParkingBoy {

    public SuperSmartParkingBoy(ParkingBoy parkingBoy) {
        super(parkingBoy);
    }

    @Override
    public ParkingTicket park(Car car) {
        ParkingLot currentParkingLot = getLargerParkingLot();
        if(Objects.nonNull(currentParkingLot)) {
            ParkingTicket parkingTicket = new ParkingTicket();
            currentParkingLot.park(car, parkingTicket);
            return parkingTicket;
        }
        this.setMessage(Messages.NOT_ENOUGH_POSITION);
        return null;
    }

    @Override
    public ParkingLot getLargerParkingLot() {
        return this.getParkingLots().stream().reduce(null, (initial, curr) -> {
            if(curr.getAvailableParkingPosition() != 0) {
                if (Objects.nonNull(initial) && Math.abs(Double.valueOf(initial.getAvailableParkingPosition())/10) >=Math.abs(Double.valueOf(curr.getAvailableParkingPosition())/10)) {
                    return initial;
                }
                return curr;
            }
            return null;
        });
    }
}
