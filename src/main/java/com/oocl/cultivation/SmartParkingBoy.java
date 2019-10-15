package com.oocl.cultivation;

import java.util.Objects;

public class SmartParkingBoy extends ParkingBoy {

    public SmartParkingBoy(ParkingBoy parkingBoy) {
        super(parkingBoy.getParkingLots());
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

    public ParkingLot getLargerParkingLot() {
        return this.getParkingLots().stream().reduce(null, (initial, curr) -> {
            if(curr.getAvailableParkingPosition() != 0) {
                if (Objects.nonNull(initial) && Math.abs(initial.getAvailableParkingPosition()) >= Math.abs(curr.getAvailableParkingPosition())) {
                    return initial;
                }
                return curr;
            }
            return null;
        });
    }

    public ParkingLot getCurrentParkingLot(ParkingTicket parkingTicket) {
        return this.getParkingLots().stream().filter(parkingLot -> parkingLot.findCar(parkingTicket)).findFirst().orElse(null);
    }
}
