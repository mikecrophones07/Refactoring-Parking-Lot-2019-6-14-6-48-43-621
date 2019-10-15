package com.oocl.cultivation;

import java.util.List;
import java.util.Objects;

public class SmartParkingBoy extends ParkingBoy {

    public SmartParkingBoy(ParkingBoy parkingBoy) {
        super(parkingBoy.getParkingLots());
    }

    private List<ParkingLot> parkingLotList = getParkingLots();

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
        return parkingLotList.stream().reduce(null, this::getMoreAvailableParkingLot);
    }

    public ParkingLot getMoreAvailableParkingLot(ParkingLot parkingLot1, ParkingLot parkingLot2) {
        if(parkingLot2.getAvailableParkingPosition() != 0) {
            if (Objects.nonNull(parkingLot1) && Math.abs(parkingLot1.getAvailableParkingPosition()) >= Math.abs(parkingLot2.getAvailableParkingPosition())) {
                return parkingLot1;
            }
            return parkingLot2;
        }
        return null;
    }

    public ParkingLot getCurrentParkingLot(ParkingTicket parkingTicket) {
        return this.getParkingLots().stream().filter(parkingLot -> parkingLot.findCar(parkingTicket)).findFirst().orElse(null);
    }
}
